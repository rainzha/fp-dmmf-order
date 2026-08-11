package org.rainzha.dmmf.app.mapper;

import org.rainzha.dmmf.domain.discriminated.OptionResult;
import org.rainzha.dmmf.domain.discriminated.SendResult;
import org.rainzha.dmmf.domain.port.inbound.AcknowledgeOrder;
import org.rainzha.dmmf.domain.port.inbound.CreateOrderAcknowledgmentLetter;
import org.rainzha.dmmf.domain.port.outbound.SendOrderAcknowledgment;
import org.rainzha.dmmf.domain.records.OrderAcknowledgment;
import org.rainzha.dmmf.domain.records.OrderAcknowledgmentSent;
import org.rainzha.dmmf.domain.records.PricedOrder;

/**
 * 完整复刻 F# acknowledgeOrder 工作流逻辑
 */
public class AcknowledgeOrderWorkflowMapper implements AcknowledgeOrder {

    @Override
    public OptionResult<OrderAcknowledgmentSent> acknowledge(
            CreateOrderAcknowledgmentLetter createLetter,
            SendOrderAcknowledgment sendMail,
            PricedOrder pricedOrder
    ) {
        // 1. 生成HTML邮件内容
        var htmlContent = createLetter.buildLetter(pricedOrder);
        // 2. 组装邮件载体
        var acknowledgment = new OrderAcknowledgment(
                pricedOrder.customerInfo().emailAddress(),
                htmlContent
        );
        // 3. 调用出站端口发送邮件
        SendResult sendResult = sendMail.send(acknowledgment);

        // 4. 分支匹配：发送成功返回Some(事件)，失败返回None
        return sendResult.match(
                () -> {
                    var event = new OrderAcknowledgmentSent(
                            pricedOrder.orderId(),
                            acknowledgment.emailAddress()
                    );
                    return OptionResult.some(event);
                },
                OptionResult::none // 方法引用简化，等价 () -> OptionResult.none()
        );
    }
}