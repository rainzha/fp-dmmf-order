package org.rainzha.dmmf.domain.port.inbound;

import org.rainzha.dmmf.domain.discriminated.OptionResult;
import org.rainzha.dmmf.domain.port.outbound.SendOrderAcknowledgment;
import org.rainzha.dmmf.domain.records.OrderAcknowledgmentSent;
import org.rainzha.dmmf.domain.records.PricedOrder;

/**
 * F# 工作流函数类型：
 * type AcknowledgeOrder =
 * CreateOrderAcknowledgmentLetter -> SendOrderAcknowledgment -> PricedOrder -> OrderAcknowledgmentSent option
 * 订单确认完整工作流入站端口：生成邮件 + 发送邮件，发送成功输出领域事件，失败返回None
 */
@FunctionalInterface
public interface AcknowledgeOrder {
    OptionResult<OrderAcknowledgmentSent> acknowledge(
            CreateOrderAcknowledgmentLetter createLetter,
            SendOrderAcknowledgment sendMail,
            PricedOrder pricedOrder
    );
}