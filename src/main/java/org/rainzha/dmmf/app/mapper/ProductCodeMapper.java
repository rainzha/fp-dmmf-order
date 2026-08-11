package org.rainzha.dmmf.app.mapper;

import org.rainzha.dmmf.domain.discriminated.ProductCode;
import org.rainzha.dmmf.domain.port.outbound.CheckProductCodeExists;

/**
 * 映射函数 toProductCode：原始字符串商品编码 → 领域ProductCode
 * 依赖出站端口校验外部商品服务
 */
public class ProductCodeMapper {

    /**
     * F#：let toProductCode (checkProductCodeExists: CheckProductCodeExists) (productCodeStr: string) : ProductCode
     */
    public static ProductCode toProductCode(
            CheckProductCodeExists checkProductCodeExists,
            String productCodeStr
    ) {
        // 直接调用出站端口校验并返回领域ProductCode
        return checkProductCodeExists.check(productCodeStr);
    }
}