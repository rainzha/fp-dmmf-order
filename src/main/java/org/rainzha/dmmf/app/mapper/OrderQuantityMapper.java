package org.rainzha.dmmf.app.mapper;

import org.rainzha.dmmf.domain.discriminated.OrderQuantity;
import org.rainzha.dmmf.domain.discriminated.ProductCode;
import org.rainzha.dmmf.domain.valueobj.KilogramQuantity;
import org.rainzha.dmmf.domain.valueobj.UnitQuantity;

import java.math.BigDecimal;

/**
 * 转换函数 toOrderQuantity：原始数量 + ProductCode → 领域 OrderQuantity 代数类型
 */
public class OrderQuantityMapper {

    /**
     * F#: let toOrderQuantity (productCode: ProductCode) (quantity: decimal) : OrderQuantity
     */
    public static OrderQuantity toOrderQuantity(ProductCode productCode, BigDecimal quantity) {
        return productCode.match(
                // Widget 分支：decimal 转 int，构建 UnitQuantity，包装为 OrderQuantity.Unit
                str50 -> {
                    int unitNum = quantity.intValueExact();
                    UnitQuantity unitQty = UnitQuantity.create(unitNum);
                    return OrderQuantity.ofUnit(unitQty);
                },
                // Gizmo 分支：decimal 直接构建 KilogramQuantity，包装为 OrderQuantity.Kilogram
                str50 -> {
                    KilogramQuantity kgQty = KilogramQuantity.create(quantity);
                    return OrderQuantity.ofKilogram(kgQty);
                }
        );
    }
}