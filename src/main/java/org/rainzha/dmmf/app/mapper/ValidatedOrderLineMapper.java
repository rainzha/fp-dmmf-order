package org.rainzha.dmmf.app.mapper;

import org.rainzha.dmmf.app.dto.UnvalidatedOrderLine;
import org.rainzha.dmmf.domain.discriminated.OrderQuantity;
import org.rainzha.dmmf.domain.discriminated.ProductCode;
import org.rainzha.dmmf.domain.port.outbound.CheckProductCodeExists;
import org.rainzha.dmmf.domain.records.ValidatedOrderLine;
import org.rainzha.dmmf.domain.valueobj.OrderLineId;

import java.math.BigDecimal;

/**
 * 转换函数 toValidatedOrderLine
 * 原始未校验订单项DTO → 领域合法ValidatedOrderLine
 */
public class ValidatedOrderLineMapper {

    public static ValidatedOrderLine toValidatedOrderLine(
            CheckProductCodeExists checkProductCodeExists,
            UnvalidatedOrderLine unvalidatedOrderLine
    ) {
        // 1. 订单行ID 原始字符串转为领域值对象
        OrderLineId orderLineId = OrderLineId.create(unvalidatedOrderLine.orderLineId());

        // 2. 商品编码字符串调用toProductCode，外部服务校验生成ProductCode
        String rawProductCode = unvalidatedOrderLine.productCode();
        ProductCode productCode = ProductCodeMapper.toProductCode(checkProductCodeExists, rawProductCode);

        // 3. 根据ProductCode类型，将原始decimal转为对应OrderQuantity
        BigDecimal rawQuantity = unvalidatedOrderLine.quantity();
        OrderQuantity quantity = OrderQuantityMapper.toOrderQuantity(productCode, rawQuantity);

        // 4. 组装完整领域ValidatedOrderLine并返回
        return new ValidatedOrderLine(orderLineId, productCode, quantity);
    }
}