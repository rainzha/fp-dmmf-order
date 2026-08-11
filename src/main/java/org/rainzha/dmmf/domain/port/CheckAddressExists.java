package org.rainzha.dmmf.domain.port;

import org.rainzha.dmmf.app.dto.UnvalidatedAddress;
import org.rainzha.dmmf.domain.port.model.CheckedAddress;

/**
 * 出站端口抽象：校验地址合法性
 * 函数签名：外部原始未校验地址DTO → 校验完成包装地址
 */
@FunctionalInterface
public interface CheckAddressExists {
    CheckedAddress check(UnvalidatedAddress unvalidatedAddress);
}