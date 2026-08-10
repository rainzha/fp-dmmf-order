package org.rainzha.dmmf.domain.records;

import org.rainzha.dmmf.domain.discriminated.OrderQuantity;
import org.rainzha.dmmf.domain.discriminated.ProductCode;
import org.rainzha.dmmf.domain.valueobj.OrderLineId;

/**
 * F# 积类型 Record 映射
 * { OrderLineId: OrderLineId
 * ProductCode: ProductCode
 * Quantity: OrderQuantity }
 */
public record ValidatedOrderLine(
        OrderLineId orderLineId,
        ProductCode productCode,
        OrderQuantity quantity
) {
}