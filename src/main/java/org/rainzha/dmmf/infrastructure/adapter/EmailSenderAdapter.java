package org.rainzha.dmmf.infrastructure.adapter;

import org.rainzha.dmmf.domain.discriminated.SendResult;
import org.rainzha.dmmf.domain.port.outbound.SendOrderAcknowledgment;
import org.rainzha.dmmf.domain.records.OrderAcknowledgment;

/**
 * 邮件发送出站端口实现，对接真实邮件服务
 */
public class EmailSenderAdapter implements SendOrderAcknowledgment {

    @Override
    public SendResult send(OrderAcknowledgment acknowledgment) {
        // 取出邮箱、HTML内容，调用第三方邮件API
        String targetEmail = acknowledgment.emailAddress().value();
        String htmlContent = acknowledgment.letter().unwrap();

        // 模拟发送逻辑，实际替换为真实邮件发送
        boolean sendSuccess = true;
        if (sendSuccess) {
            return SendResult.SENT;
        } else {
            return SendResult.NOT_SENT;
        }
    }
}