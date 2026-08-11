package org.rainzha.dmmf.domain.records;

import org.rainzha.dmmf.domain.valueobj.BillingAmount;
import org.rainzha.dmmf.domain.valueobj.OrderId;

import java.util.List;

/**
 * F# 积类型记录 PricedOrder
 * { OrderId: OrderId
 * CustomerInfo: CustomerInfo
 * ShippingAddress: Address
 * BillingAddress: Address
 * Lines: PricedOrderLine list
 * AmountToBill: BillingAmount }
 */
public record PricedOrder(
        OrderId orderId,
        CustomerInfo customerInfo,
        Address shippingAddress,
        Address billingAddress,
        List<PricedOrderLine> lines,
        BillingAmount amountToBill
) {
    // 紧凑构造器：仅拦截顶层字段null，子领域模型自身校验由各自create方法保证
    public PricedOrder {
        if (orderId == null) throw new IllegalArgumentException("OrderId cannot be null");
        if (customerInfo == null) throw new IllegalArgumentException("CustomerInfo cannot be null");
        if (shippingAddress == null) throw new IllegalArgumentException("ShippingAddress cannot be null");
        if (billingAddress == null) throw new IllegalArgumentException("BillingAddress cannot be null");
        if (lines == null) throw new IllegalArgumentException("PricedOrderLine list cannot be null");
        if (amountToBill == null) throw new IllegalArgumentException("AmountToBill cannot be null");
    }
}