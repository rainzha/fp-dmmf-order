package org.rainzha.dmmf.domain.valueobj;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailAddressTest {

    // 合法邮箱，create 创建正常实例
    @Test
    void create_ValidEmail_ReturnEmailAddress() {
        String rawMail = "user@company.com";
        EmailAddress email = EmailAddress.create(rawMail);
        assertThat(email.value()).isEqualTo(rawMail);
    }

    // 空字符串、空白、null 校验
    @Test
    void create_EmptyOrBlankOrNull_ThrowMsgMustNotEmpty() {
        assertThatThrownBy(() -> EmailAddress.create(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Email must not be empty");

        assertThatThrownBy(() -> EmailAddress.create("    "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Email must not be empty");

        assertThatThrownBy(() -> EmailAddress.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Email must not be empty");
    }

    // 长度超过50字符校验
    @Test
    void create_Over50Chars_ThrowMsgTooLong() {
        // 拼接总长51位邮箱
        String longMail = "a".repeat(48) + "@x.cn";
        assertThatThrownBy(() -> EmailAddress.create(longMail))
                .hasMessage("Email cannot be longer than 50 characters");
    }

    // 缺少@符号校验
    @Test
    void create_MissingAtSymbol_ThrowMsgNeedAt() {
        assertThatThrownBy(() -> EmailAddress.create("usercompany.com"))
                .hasMessage("Email must contain @");
    }

    // createOption 合法输入返回有值 Optional
    @Test
    void createOption_ValidMail_ReturnPresentOptional() {
        Optional<EmailAddress> opt = EmailAddress.createOption("demo@qq.com");
        assertThat(opt).isPresent();
        assertThat(opt.get().value()).isEqualTo("demo@qq.com");
    }

    // createOption 各类非法输入统一返回空 Optional
    @Test
    void createOption_InvalidInput_ReturnEmptyOptional() {
        assertThat(EmailAddress.createOption("")).isEmpty();
        assertThat(EmailAddress.createOption("no-at-mail")).isEmpty();
        assertThat(EmailAddress.createOption("a".repeat(48) + "@cn")).isEmpty();
        assertThat(EmailAddress.createOption(null)).isEmpty();
    }

    // 兜底防护：直接 new 非法邮箱会抛出拦截提示
    @Test
    void directNew_InvalidValue_ThrowGuardTip() {
        assertThatThrownBy(() -> new EmailAddress("badmail"))
                .hasMessage("禁止直接 new EmailAddress，请使用 EmailAddress.create()");
    }
}