package org.rainzha.dmmf.domain.valueobj;

import org.rainzha.dmmf.domain.discriminated.OrderQuantity;

import java.math.BigDecimal;

/**
 * 对应 F# type Price = private Price of decimal
 * 使用Java Record + 紧凑构造器拦截非法new，复刻私有单值DU约束
 */
public record Price(BigDecimal value) {

    /**
     * Record紧凑构造器：拦截外部直接 new Price(xxx) 非法构造
     * 强制使用者必须走 Price.create() 智能构造器
     */
    public Price {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("请勿直接 new Price，请使用 Price.create() 构造合法实例");
        }
    }

    /**
     * F# Price.create 智能构造器，唯一合法创建入口
     */
    public static Price create(BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("Price value cannot be null");
        }
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        return new Price(value);
    }

    /**
     * F# Price.multiply 总价计算函数
     * 数量 × 单价，返回全新合法Price
     */
    public static Price multiply(OrderQuantity qty, Price price) {
        BigDecimal unitPrice = price.value();
        BigDecimal quantityDecimal = qty.match(
                unitQty -> BigDecimal.valueOf(unitQty.value()),
                kgQty -> kgQty.value()
        );
        BigDecimal totalAmount = unitPrice.multiply(quantityDecimal);
        return create(totalAmount);
    }
}