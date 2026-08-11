package org.rainzha.dmmf.infrastructure.adapter;

import org.rainzha.dmmf.domain.discriminated.ProductCode;
import org.rainzha.dmmf.domain.port.outbound.GetProductPrice;
import org.rainzha.dmmf.domain.valueobj.Price;
import org.rainzha.dmmf.domain.valueobj.String50;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 出站端口 GetProductPrice 基础设施适配器
 */
public class ProductPriceAdapter implements GetProductPrice {

    // 模拟商品价格数据源
    private static final Map<String, BigDecimal> PRODUCT_PRICE_STORAGE = Map.of(
            "WIDGET001", new BigDecimal("19.99"),
            "GADGET002", new BigDecimal("49.50")
    );

    @Override
    public Price getPrice(ProductCode productCode) {
        // 核心修复：通过match模式匹配取出内部字符串
        String codeText = productCode.match(
                String50::value,
                String50::value
        );

        BigDecimal rawPrice = PRODUCT_PRICE_STORAGE.get(codeText);
        if (rawPrice == null) {
            throw new IllegalArgumentException("未查询到该商品定价：" + codeText);
        }
        return Price.create(rawPrice);
    }
}