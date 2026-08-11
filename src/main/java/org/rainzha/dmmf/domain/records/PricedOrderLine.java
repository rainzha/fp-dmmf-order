package org.rainzha.dmmf.domain.records;

import org.rainzha.dmmf.domain.discriminated.OrderQuantity;
import org.rainzha.dmmf.domain.discriminated.ProductCode;
import org.rainzha.dmmf.domain.valueobj.OrderLineId;
import org.rainzha.dmmf.domain.valueobj.Price;

/**
 * F# type PricedOrderLine = { OrderLineId; ProductCode; Quantity; LinePrice }
 * 定价完成后的订单行领域记录，全部使用强类型值对象/代数类型
 */
public record PricedOrderLine(
        OrderLineId orderLineId,
        ProductCode productCode,
        OrderQuantity quantity,
        Price linePrice
) {
    // 可选：紧凑构造器统一空值拦截（和你其他Record规范对齐）
    public PricedOrderLine {
        if (orderLineId == null) throw new IllegalArgumentException("OrderLineId cannot be null");
        if (productCode == null) throw new IllegalArgumentException("ProductCode cannot be null");
        if (quantity == null) throw new IllegalArgumentException("Quantity cannot be null");
        if (linePrice == null) throw new IllegalArgumentException("LinePrice cannot be null");
    }
}