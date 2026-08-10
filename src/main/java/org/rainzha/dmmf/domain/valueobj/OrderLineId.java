package org.rainzha.dmmf.domain.valueobj;

import java.util.Optional;

/**
 * 对齐 F# private OrderLineId of string 语义
 */
public record OrderLineId(String value) {

    // 紧凑构造器兜底防护：禁止外部直接 new 传入非法值
    public OrderLineId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("禁止直接 new OrderLineId，请使用 OrderLineId.create()");
        }
    }

    // 对应 F# OrderLineId.create，非法输入直接抛异常
    public static OrderLineId create(String s) {
        if (s == null || s.isBlank()) {
            throw new IllegalArgumentException("OrderLineId cannot be empty");
        }
        return new OrderLineId(s);
    }

    // 容错创建，非法返回 Optional.empty()，不抛异常
    public static Optional<OrderLineId> createOption(String s) {
        try {
            return Optional.of(create(s));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}