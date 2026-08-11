package org.rainzha.dmmf.domain.records;

import org.rainzha.dmmf.domain.valueobj.BillingAmount;
import org.rainzha.dmmf.domain.valueobj.OrderId;

/**
 * Event to send to billing context
 * Will only be created if the AmountToBill is not zero
 * F# type BillableOrderPlaced =
 * { OrderId: OrderId
 * BillingAddress: Address
 * AmountToBill: BillingAmount }
 * 下发给账单上下文的可计费订单事件
 * 约束：仅当计费金额不为0时才生成该事件
 */
public record BillableOrderPlaced(
        OrderId orderId,
        Address billingAddress,
        BillingAmount amountToBill
) {
    // 紧凑构造器：强校验所有值对象非空，贴合领域约束
    public BillableOrderPlaced {
        if (orderId == null) {
            throw new IllegalArgumentException("OrderId 不能为空");
        }
        if (billingAddress == null) {
            throw new IllegalArgumentException("账单地址 BillingAddress 不能为空");
        }
        if (amountToBill == null) {
            throw new IllegalArgumentException("计费金额 AmountToBill 不能为空");
        }
    }
}