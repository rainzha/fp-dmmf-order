package org.rainzha.dmmf.domain.port.inbound;

import org.rainzha.dmmf.domain.port.outbound.GetProductPrice;
import org.rainzha.dmmf.domain.records.PricedOrderLine;
import org.rainzha.dmmf.domain.records.ValidatedOrderLine;

/**
 * F# 函数别名 type ToPricedOrderLine = GetProductPrice -> ValidatedOrderLine -> PricedOrderLine
 * 入站端口：将校验完成的订单行，根据外部价格服务生成定价订单行
 */
@FunctionalInterface
public interface ToPricedOrderLine {
    PricedOrderLine convert(
            GetProductPrice getProductPrice,
            ValidatedOrderLine validatedOrderLine
    );
}