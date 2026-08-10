package org.rainzha.dmmf.domain.valueobj;

import java.util.Optional;

public record UnitQuantity(int value) {

    // 兜底拦截直接 new
    public UnitQuantity {
        if (value < 1 || value > 1000) {
            throw new IllegalArgumentException("Do not instantiate UnitQuantity directly, use UnitQuantity.create()");
        }
    }

    public static UnitQuantity create(int qty) {
        if (qty < 1) {
            throw new IllegalArgumentException("Unit quantity must be greater than zero");
        }
        if (qty > 1000) {
            throw new IllegalArgumentException("Unit quantity cannot be more than 1000");
        }
        return new UnitQuantity(qty);
    }

    public static Optional<UnitQuantity> createOption(int qty) {
        try {
            return Optional.of(create(qty));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}