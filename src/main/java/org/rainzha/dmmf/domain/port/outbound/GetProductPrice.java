package org.rainzha.dmmf.domain.port.outbound;

import org.rainzha.dmmf.domain.discriminated.ProductCode;
import org.rainzha.dmmf.domain.valueobj.Price;

/**
 * F# 函数类型别名：type GetProductPrice = ProductCode -> Price
 * 出站端口：领域查询外部商品定价服务
 */
@FunctionalInterface
public interface GetProductPrice {
    Price getPrice(ProductCode productCode);
}