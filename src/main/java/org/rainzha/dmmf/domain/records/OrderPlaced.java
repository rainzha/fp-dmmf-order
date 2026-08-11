package org.rainzha.dmmf.domain.records;

/**
 * Event to send to shipping context
 * 下发至物流上下文的领域事件：订单已计价完成
 * type OrderPlaced = PricedOrder
 */
public record OrderPlaced(PricedOrder pricedOrder) {
    // 空值防护
    public OrderPlaced {
        if (pricedOrder == null) {
            throw new IllegalArgumentException("PricedOrder 不能为空");
        }
    }
}