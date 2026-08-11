package org.rainzha.dmmf.app.mapper;

import org.rainzha.dmmf.domain.port.inbound.PriceOrder;
import org.rainzha.dmmf.domain.port.inbound.ToPricedOrderLine;
import org.rainzha.dmmf.domain.port.outbound.GetProductPrice;
import org.rainzha.dmmf.domain.records.PricedOrder;
import org.rainzha.dmmf.domain.records.PricedOrderLine;
import org.rainzha.dmmf.domain.records.ValidatedOrder;
import org.rainzha.dmmf.domain.valueobj.BillingAmount;
import org.rainzha.dmmf.domain.valueobj.Price;

import java.util.List;

/**
 * 完整还原F# let priceOrder 定价主逻辑
 * 实现入站端口 PriceOrder，完成整单定价
 */
public class PricedOrderMapper implements PriceOrder {

    private final ToPricedOrderLine toPricedOrderLine;

    // 注入行定价转换器
    public PricedOrderMapper(ToPricedOrderLine toPricedOrderLine) {
        this.toPricedOrderLine = toPricedOrderLine;
    }

    @Override
    public PricedOrder price(GetProductPrice getProductPrice, ValidatedOrder validatedOrder) {
        // 1. 逐行定价：List.map (toPricedOrderLine getProductPrice)
        List<PricedOrderLine> pricedLines = validatedOrder.lines().stream()
                .map(line -> toPricedOrderLine.convert(getProductPrice, line))
                .toList();

        // 2. 提取所有行总价，累加得到账单总金额
        List<Price> allLinePrices = pricedLines.stream()
                .map(PricedOrderLine::linePrice)
                .toList();
        BillingAmount totalBill = BillingAmount.sumPrices(allLinePrices);

        // 3. 组装PricedOrder，F#逻辑：BillingAddress复用ShippingAddress
        return new PricedOrder(
                validatedOrder.orderId(),
                validatedOrder.customerInfo(),
                validatedOrder.shippingAddress(),
                validatedOrder.shippingAddress(),
                pricedLines,
                totalBill
        );
    }
}