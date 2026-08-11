package org.rainzha.dmmf.domain.port.inbound;

import org.rainzha.dmmf.app.dto.UnvalidatedOrder;
import org.rainzha.dmmf.domain.port.outbound.CheckAddressExists;
import org.rainzha.dmmf.domain.port.outbound.CheckProductCodeExists;
import org.rainzha.dmmf.domain.records.ValidatedOrder;

/**
 * 入站端口契约：校验原始订单生成合法领域订单
 * 对应F#类型别名：ValidateOrder = CheckProductCodeExists -> CheckAddressExists -> UnvalidatedOrder -> ValidatedOrder
 */
@FunctionalInterface
public interface ValidateOrder {
    ValidatedOrder validate(
            CheckProductCodeExists checkProductCodeExists,
            CheckAddressExists checkAddressExists,
            UnvalidatedOrder unvalidatedOrder
    );
}