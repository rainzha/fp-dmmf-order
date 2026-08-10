package org.rainzha.dmmf.domain.valueobj;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ZipCodeTest {

    @Test
    void create_ValidInput_ReturnValidInstance() {
        ZipCode zip = ZipCode.create("210001");
        assertThat(zip.value()).isEqualTo("210001");
    }

    @Test
    void create_EmptyString_ThrowBusinessError() {
        assertThatThrownBy(() -> ZipCode.create(""))
                .hasMessage("zip must not empty");
    }

    @Test
    void create_OverMaxLength_ThrowBusinessError() {
        assertThatThrownBy(() -> ZipCode.create("12345678901"))
                .hasMessage("zip too long");
    }

    @Test
    void createOption_InvalidInput_ReturnEmptyOptional() {
        assertThat(ZipCode.createOption("")).isEmpty();
        assertThat(ZipCode.createOption("12345678901")).isEmpty();
    }

    @Test
    void createOption_ValidInput_ReturnPresentOptional() {
        Optional<ZipCode> opt = ZipCode.createOption("100000");
        assertThat(opt).isPresent();
        assertThat(opt.get().value()).isEqualTo("100000");
    }

    @Test
    void directNew_IllegalValue_ThrowGuardError() {
        assertThatThrownBy(() -> new ZipCode("12345678901"))
                .hasMessage("禁止直接 new ZipCode，请使用 ZipCode.create()");
    }
}