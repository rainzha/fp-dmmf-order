package org.rainzha.dmmf.app.mapper;

import org.rainzha.dmmf.domain.port.inbound.CreateOrderAcknowledgmentLetter;
import org.rainzha.dmmf.domain.records.PricedOrder;
import org.rainzha.dmmf.domain.valueobj.common.HtmlString;

/**
 * 实现生成订单确认HTML邮件的端口逻辑
 */
public class OrderLetterMapper implements CreateOrderAcknowledgmentLetter {

    @Override
    public HtmlString buildLetter(PricedOrder pricedOrder) {
        // 拼接HTML模板，实际项目可抽模板工具类
        String htmlContent = """
                <html>
                    <body>
                        <h1>订单确认通知</h1>
                        <p>订单编号：%s</p>
                        <p>应付金额：%s</p>
                    </body>
                </html>
                """.formatted(
                pricedOrder.orderId().value(),
                pricedOrder.amountToBill().value().value()
        );
        return HtmlString.create(htmlContent);
    }
}