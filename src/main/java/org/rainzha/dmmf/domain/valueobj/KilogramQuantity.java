package org.rainzha.dmmf.domain.valueobj;

import java.math.BigDecimal;
import java.util.Optional;

public record KilogramQuantity(BigDecimal value) {

    public KilogramQuantity {
        BigDecimal min = BigDecimal.ZERO;
        BigDecimal max = new BigDecimal("100");
        if (value.compareTo(min) <= 0 || value.compareTo(max) > 0) {
            throw new IllegalArgumentException("Do not instantiate KilogramQuantity directly, use KilogramQuantity.create()");
        }
    }

    public static KilogramQuantity create(BigDecimal qty) {
        BigDecimal min = BigDecimal.ZERO;
        BigDecimal max = new BigDecimal("100");
        if (qty.compareTo(min) <= 0) {
            throw new IllegalArgumentException("Kilogram quantity must be greater than zero");
        }
        if (qty.compareTo(max) > 0) {
            throw new IllegalArgumentException("Kilogram quantity cannot be more than 100");
        }
        return new KilogramQuantity(qty);
    }

    // 方便传入字符串快速构建
    public static KilogramQuantity create(String qtyStr) {
        return create(new BigDecimal(qtyStr));
    }

    public static Optional<KilogramQuantity> createOption(BigDecimal qty) {
        try {
            return Optional.of(create(qty));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}