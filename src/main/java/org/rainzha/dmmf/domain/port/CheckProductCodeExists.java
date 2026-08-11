package org.rainzha.dmmf.domain.port;

import org.rainzha.dmmf.domain.discriminated.ProductCode;

/**
 * 出站端口抽象：校验商品编码是否存在
 * 函数签名：String(原始编码) → ProductCode(领域合法类型)
 */
@FunctionalInterface
public interface CheckProductCodeExists {
    ProductCode check(String rawProductCode);
}