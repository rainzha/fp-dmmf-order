package org.rainzha.dmmf.domain.port.outbound;

import org.rainzha.dmmf.domain.discriminated.SendResult;
import org.rainzha.dmmf.domain.records.OrderAcknowledgment;

/**
 * F# 函数别名：type SendOrderAcknowledgment = OrderAcknowledgment -> SendResult
 * 出站端口：调用外部邮件服务发送确认邮件
 */
@FunctionalInterface
public interface SendOrderAcknowledgment {
    SendResult send(OrderAcknowledgment acknowledgment);
}