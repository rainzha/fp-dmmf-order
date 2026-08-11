package org.rainzha.dmmf.app.dto;

import java.util.List;

/**
 * 完整订单原始外部输入DTO，聚合所有未校验子模型
 */
public record UnvalidatedOrder(
        String orderId,
        UnvalidatedCustomerInfo customerInfo,
        UnvalidatedAddress shippingAddress,
        List<UnvalidatedOrderLine> lines
) {
}