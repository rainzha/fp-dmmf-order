package org.rainzha.dmmf.domain.valueobj;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class String50Test {

    @Test
    void create_ValidText_ReturnInstance() {
        String50 str50 = String50.create("Beijing");
        assertThat(str50.value()).isEqualTo("Beijing");
    }

    @Test
    void create_EmptyBlank_Throw() {
        assertThatThrownBy(() -> String50.create(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Must not be empty");
        assertThatThrownBy(() -> String50.create("   ")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> String50.create(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void create_Over50Char_Throw() {
        String longStr = "A".repeat(51);
        assertThatThrownBy(() -> String50.create(longStr))
                .hasMessage("Cannot be longer than 50 characters");
    }

    @Test
    void createOption_Invalid_ReturnEmptyOptional() {
        Optional<String50> emptyOpt = String50.createOption("");
        Optional<String50> longOpt = String50.createOption("X".repeat(60));
        assertThat(emptyOpt).isEmpty();
        assertThat(longOpt).isEmpty();
    }

    @Test
    void createOption_Valid_ReturnPresent() {
        Optional<String50> opt = String50.createOption("Nanjing");
        assertThat(opt).isPresent();
        assertThat(opt.get().value()).isEqualTo("Nanjing");
    }

    @Test
    void directNew_Invalid_ThrowGuardMessage() {
        assertThatThrownBy(() -> new String50(""))
                .hasMessage("禁止直接 new String50，请使用 String50.create()");
    }
}