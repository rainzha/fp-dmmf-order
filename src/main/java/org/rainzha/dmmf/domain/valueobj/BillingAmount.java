package org.rainzha.dmmf.domain.valueobj;

import java.math.BigDecimal;
import java.util.List;

/**
 * F# type BillingAmount = private BillingAmount of Price
 * 单包装值对象，内部仅封装 Price，私有构造拦截非法new
 */
public record BillingAmount(Price value) {

    /**
     * Record紧凑构造器：拦截外部直接 new BillingAmount(null)
     */
    public BillingAmount {
        if (value == null) {
            throw new IllegalArgumentException("请勿直接 new BillingAmount，请使用 BillingAmount.create()");
        }
    }

    /**
     * F# BillingAmount.create 唯一合法构造入口
     */
    public static BillingAmount create(Price price) {
        if (price == null) {
            throw new IllegalArgumentException("Price cannot be null for BillingAmount");
        }
        return new BillingAmount(price);
    }

    /**
     * F# BillingAmount.sumPrices：批量累加所有Price，生成总账单金额
     */
    public static BillingAmount sumPrices(List<Price> prices) {
        // 初始总和0
        BigDecimal total = BigDecimal.ZERO;
        for (Price p : prices) {
            total = total.add(p.value());
        }
        // 总和走Price.create校验正数，再包装为BillingAmount
        Price totalPrice = Price.create(total);
        return create(totalPrice);
    }
}