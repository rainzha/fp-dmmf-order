package org.rainzha.dmmf.domain.valueobj;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UnitQuantityTest {

    @Test
    void create_WithValidValue_ReturnInstance() {
        UnitQuantity qty = UnitQuantity.create(500);
        assertThat(qty.value()).isEqualTo(500);
    }

    @Test
    void create_WithZeroValue_ThrowException() {
        assertThatThrownBy(() -> UnitQuantity.create(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Unit quantity must be greater than zero");

        assertThatThrownBy(() -> UnitQuantity.create(-10))
                .hasMessage("Unit quantity must be greater than zero");
    }

    @Test
    void create_WithValueOverMax_ThrowException() {
        assertThatThrownBy(() -> UnitQuantity.create(1001))
                .hasMessage("Unit quantity cannot be more than 1000");
    }

    @Test
    void createOption_WithValidValue_ReturnPresentOptional() {
        Optional<UnitQuantity> opt = UnitQuantity.createOption(100);
        assertThat(opt).isPresent();
        assertThat(opt.get().value()).isEqualTo(100);
    }

    @Test
    void createOption_WithInvalidValue_ReturnEmptyOptional() {
        assertThat(UnitQuantity.createOption(0)).isEmpty();
        assertThat(UnitQuantity.createOption(9999)).isEmpty();
    }

    @Test
    void newDirectly_ThrowPreventException() {
        assertThatThrownBy(() -> new UnitQuantity(0))
                .hasMessage("Do not instantiate UnitQuantity directly, use UnitQuantity.create()");
    }
}