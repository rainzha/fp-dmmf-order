package org.rainzha.dmmf.domain.valueobj;

import java.util.Optional;

public record EmailAddress(String value) {

    // 兜底防护：防止外部直接 new 传入非法数据
    public EmailAddress {
        if (value == null || value.isBlank() || value.length() > 50 || !value.contains("@")) {
            throw new IllegalArgumentException("禁止直接 new EmailAddress，请使用 EmailAddress.create()");
        }
    }

    // 对应 F# EmailAddress.create，校验失败抛异常
    public static EmailAddress create(String str) {
        if (str == null || str.isBlank()) {
            throw new IllegalArgumentException("Email must not be empty");
        }
        if (str.length() > 50) {
            throw new IllegalArgumentException("Email cannot be longer than 50 characters");
        }
        if (!str.contains("@")) {
            throw new IllegalArgumentException("Email must contain @");
        }
        return new EmailAddress(str);
    }

    public static Optional<EmailAddress> createOption(String str) {
        try {
            return Optional.of(create(str));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}