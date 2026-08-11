package org.rainzha.dmmf.domain.port.inbound;

import org.rainzha.dmmf.domain.discriminated.OptionResult;
import org.rainzha.dmmf.domain.discriminated.PlaceOrderEvent;
import org.rainzha.dmmf.domain.records.OrderAcknowledgmentSent;
import org.rainzha.dmmf.domain.records.PricedOrder;

import java.util.List;

/**
 * F# type CreateEvents =
 * PricedOrder -> OrderAcknowledgmentSent option -> PlaceOrderEvent list
 * 输入1：计价完成订单 PricedOrder
 * 输入2：上一步产生的确认事件（可选）OrderAcknowledgmentSent option
 * 输出：一组下单领域事件 List<PlaceOrderEvent>
 */
@FunctionalInterface
public interface CreateEvents {
    List<PlaceOrderEvent> create(
            PricedOrder pricedOrder,
            OptionResult<OrderAcknowledgmentSent> ackEventOpt
    );
}