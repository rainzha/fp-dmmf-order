package org.rainzha.dmmf.domain.port.inbound;

import org.rainzha.dmmf.domain.port.outbound.GetProductPrice;
import org.rainzha.dmmf.domain.records.PricedOrder;
import org.rainzha.dmmf.domain.records.ValidatedOrder;

/**
 * F# type PriceOrder = GetProductPrice -> ValidatedOrder -> PricedOrder
 * 入站端口：给校验完成订单批量计算价格，生成定价订单
 */
@FunctionalInterface
public interface PriceOrder {
    PricedOrder price(GetProductPrice getProductPrice, ValidatedOrder validatedOrder);
}