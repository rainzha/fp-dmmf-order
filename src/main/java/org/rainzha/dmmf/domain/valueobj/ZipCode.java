package org.rainzha.dmmf.domain.valueobj;

import java.util.Optional;

/**
 * 对齐 F# private ZipCode of string 语义
 */
public record ZipCode(String value) {

    // 紧凑构造器兜底防护：防止外部直接 new 传入非法值
    public ZipCode {
        if (value == null || value.isEmpty() || value.length() > 10) {
            throw new IllegalArgumentException("禁止直接 new ZipCode，请使用 ZipCode.create()");
        }
    }

    // 对应 F# ZipCode.create，非法输入直接抛异常
    public static ZipCode create(String s) {
        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException("zip must not empty");
        }
        if (s.length() > 10) {
            throw new IllegalArgumentException("zip too long");
        }
        return new ZipCode(s);
    }

    // 容错创建，非法返回 Optional.empty()，不抛异常
    public static Optional<ZipCode> createOption(String s) {
        try {
            return Optional.of(create(s));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}