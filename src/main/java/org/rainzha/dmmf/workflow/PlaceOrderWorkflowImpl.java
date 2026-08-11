package org.rainzha.dmmf.workflow;

import org.rainzha.dmmf.app.dto.UnvalidatedOrder;
import org.rainzha.dmmf.domain.discriminated.OptionResult;
import org.rainzha.dmmf.domain.discriminated.PlaceOrderEvent;
import org.rainzha.dmmf.domain.port.inbound.*;
import org.rainzha.dmmf.domain.port.outbound.CheckAddressExists;
import org.rainzha.dmmf.domain.port.outbound.CheckProductCodeExists;
import org.rainzha.dmmf.domain.port.outbound.GetProductPrice;
import org.rainzha.dmmf.domain.port.outbound.SendOrderAcknowledgment;
import org.rainzha.dmmf.domain.records.OrderAcknowledgmentSent;
import org.rainzha.dmmf.domain.records.PricedOrder;
import org.rainzha.dmmf.domain.records.ValidatedOrder;

import java.util.List;

/**
 * 等价F# let placeOrder 函数
 * 构造函数 = F#外层5个柯里化依赖参数
 * 实现PlaceOrderWorkflow接口 = F#返回的 fun unvalidatedOrder -> ...
 */
public class PlaceOrderWorkflowImpl implements PlaceOrderWorkflow {
    // 保存所有外层依赖（等价F#闭包捕获外层参数）
    private final CheckProductCodeExists checkProductCodeExists;
    private final CheckAddressExists checkAddressExists;
    private final GetProductPrice getProductPrice;
    private final CreateOrderAcknowledgmentLetter createAcknowledgmentLetter;
    private final SendOrderAcknowledgment sendAcknowledgment;

    // 入站端口接口，不绑定任何具体实现（墙外OrderMapper通过构造注入）
    private final ValidateOrder validateOrder;
    private final PriceOrder priceOrder;
    private final AcknowledgeOrder acknowledgeOrder;
    private final CreateEvents createEvents;

    // ========== 完全对应F#外层5个参数，顺序丝毫不差 ==========
    public PlaceOrderWorkflowImpl(
            CheckProductCodeExists checkProductCodeExists,
            CheckAddressExists checkAddressExists,
            GetProductPrice getProductPrice,
            CreateOrderAcknowledgmentLetter createAcknowledgmentLetter,
            SendOrderAcknowledgment sendAcknowledgment,
            ValidateOrder validateOrder,
            PriceOrder priceOrder,
            AcknowledgeOrder acknowledgeOrder,
            CreateEvents createEvents
    ) {
        this.checkProductCodeExists = checkProductCodeExists;
        this.checkAddressExists = checkAddressExists;
        this.getProductPrice = getProductPrice;
        this.createAcknowledgmentLetter = createAcknowledgmentLetter;
        this.sendAcknowledgment = sendAcknowledgment;
        this.validateOrder = validateOrder;
        this.priceOrder = priceOrder;
        this.acknowledgeOrder = acknowledgeOrder;
        this.createEvents = createEvents;
    }

    // ========== 等价F# fun unvalidatedOrder -> 内部流水线 ==========
    @Override
    public List<PlaceOrderEvent> run(UnvalidatedOrder unvalidatedOrder) {
        // unvalidatedOrder |> validateOrder checkProductCodeExists checkAddressExists
        ValidatedOrder validatedOrder = validateOrder.validate(
                this.checkProductCodeExists,
                this.checkAddressExists,
                unvalidatedOrder
        );

        // validatedOrder |> priceOrder getProductPrice
        PricedOrder pricedOrder = priceOrder.price(
                this.getProductPrice,
                validatedOrder
        );

        // pricedOrder |> acknowledgeOrder createAcknowledgmentLetter sendAcknowledgment
        OptionResult<OrderAcknowledgmentSent> acknowledgementOption = acknowledgeOrder.acknowledge(
                createAcknowledgmentLetter,
                sendAcknowledgment,
                pricedOrder
        );

        // createEvents pricedOrder acknowledgementOption
        return createEvents.create(pricedOrder, acknowledgementOption);
    }
}