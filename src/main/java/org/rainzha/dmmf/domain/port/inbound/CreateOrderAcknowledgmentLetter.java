package org.rainzha.dmmf.domain.port.inbound;

import org.rainzha.dmmf.domain.records.PricedOrder;
import org.rainzha.dmmf.domain.valueobj.common.HtmlString;

/**
 * F# 函数别名：type CreateOrderAcknowledgmentLetter = PricedOrder -> HtmlString
 * 入站端口：根据定价订单生成邮件HTML模板
 */
@FunctionalInterface
public interface CreateOrderAcknowledgmentLetter {
    HtmlString buildLetter(PricedOrder pricedOrder);
}