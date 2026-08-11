package org.rainzha.dmmf.domain.records;

import org.rainzha.dmmf.domain.valueobj.EmailAddress;
import org.rainzha.dmmf.domain.valueobj.OrderId;

/**
 * F# 领域事件记录
 * type OrderAcknowledgmentSent =
 * { OrderId: OrderId
 * EmailAddress: EmailAddress }
 * 订单确认邮件已发送领域事件
 */
public record OrderAcknowledgmentSent(
        OrderId orderId,
        EmailAddress emailAddress
) {
    // 统一Record空值防护
    public OrderAcknowledgmentSent {
        if (orderId == null) {
            throw new IllegalArgumentException("OrderId 不能为空");
        }
        if (emailAddress == null) {
            throw new IllegalArgumentException("EmailAddress 不能为空");
        }
    }
}