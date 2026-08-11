package org.rainzha.dmmf.domain.records;

import org.rainzha.dmmf.domain.valueobj.EmailAddress;
import org.rainzha.dmmf.domain.valueobj.common.HtmlString;

/**
 * F# 记录类型
 * type OrderAcknowledgment =
 * { EmailAddress: EmailAddress
 * Letter: HtmlString }
 * 订单确认邮件载体，包含收件邮箱与HTML邮件正文
 */
public record OrderAcknowledgment(
        EmailAddress emailAddress,
        HtmlString letter
) {
    // 紧凑构造器仅拦截顶层字段null，子值对象自身业务规则由各自create()保证
    public OrderAcknowledgment {
        if (emailAddress == null) {
            throw new IllegalArgumentException("EmailAddress cannot be null");
        }
        if (letter == null) {
            throw new IllegalArgumentException("HtmlString letter cannot be null");
        }
    }
}