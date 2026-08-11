package org.rainzha.dmmf.app.dto;

import java.math.BigDecimal;

/**
 * 未校验订单项原始输入DTO，数值使用原生BigDecimal，不使用领域值对象
 */
public record UnvalidatedOrderLine(
        String orderLineId,
        String productCode,
        BigDecimal quantity
) {
}