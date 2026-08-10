package org.rainzha.dmmf.domain.valueobj;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class KilogramQuantityTest {
    private static final BigDecimal MAX_KG = new BigDecimal("100");
    private static final BigDecimal ZERO = BigDecimal.ZERO;

    @Test
    void create_WithValidDecimal_ReturnInstance() {
        KilogramQuantity kg = KilogramQuantity.create(new BigDecimal("50.25"));
        assertThat(kg.value()).isEqualByComparingTo(new BigDecimal("50.25"));
    }

    @Test
    void create_WithStringParam_ReturnInstance() {
        KilogramQuantity kg = KilogramQuantity.create("12.8");
        assertThat(kg.value()).isEqualByComparingTo(new BigDecimal("12.8"));
    }

    @Test
    void create_WithZeroOrNegativeValue_ThrowException() {
        assertThatThrownBy(() -> KilogramQuantity.create(ZERO))
                .hasMessage("Kilogram quantity must be greater than zero");
        assertThatThrownBy(() -> KilogramQuantity.create(new BigDecimal("-5")))
                .hasMessage("Kilogram quantity must be greater than zero");
    }

    @Test
    void create_WithValueOverMaxKg_ThrowException() {
        assertThatThrownBy(() -> KilogramQuantity.create(new BigDecimal("100.01")))
                .hasMessage("Kilogram quantity cannot be more than 100");
    }

    @Test
    void createOption_WithValidValue_ReturnPresentOptional() {
        Optional<KilogramQuantity> opt = KilogramQuantity.createOption(new BigDecimal("88.9"));
        assertThat(opt).isPresent();
    }

    @Test
    void createOption_WithInvalidValue_ReturnEmptyOptional() {
        assertThat(KilogramQuantity.createOption(ZERO)).isEmpty();
        assertThat(KilogramQuantity.createOption(new BigDecimal("200"))).isEmpty();
    }

    @Test
    void newDirectly_ThrowPreventException() {
        assertThatThrownBy(() -> new KilogramQuantity(ZERO))
                .hasMessage("Do not instantiate KilogramQuantity directly, use KilogramQuantity.create()");
    }
}