package org.rainzha.dmmf.domain.valueobj;

import java.util.Optional;

/**
 * DMMF 通用短文本值对象：姓名、街道、城市等，最大50字符，非空
 * 对齐 F# private String50 of string 语义
 */
public record String50(String value) {

    // 紧凑构造器兜底防护：防止外部直接 new 传入非法值
    public String50 {
        if (value == null || value.isBlank() || value.length() > 50) {
            throw new IllegalArgumentException("禁止直接 new String50，请使用 String50.create()");
        }
    }

    // 对应 F# String50.create，非法输入直接抛异常
    public static String50 create(String str) {
        if (str == null || str.isBlank()) {
            throw new IllegalArgumentException("Must not be empty");
        }
        if (str.length() > 50) {
            throw new IllegalArgumentException("Cannot be longer than 50 characters");
        }
        return new String50(str);
    }

    // 对应 F# String50.createOption，非法返回 Optional.empty()，不抛异常
    public static Optional<String50> createOption(String str) {
        try {
            return Optional.of(create(str));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}