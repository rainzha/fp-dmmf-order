package org.rainzha.dmmf.domain.valueobj;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderLineIdTest {

    @Test
    void create_ValidString_ReturnInstance() {
        OrderLineId id = OrderLineId.create("LINE_0001");
        assertThat(id.value()).isEqualTo("LINE_0001");
    }

    @Test
    void create_BlankString_ThrowBusinessException() {
        assertThatThrownBy(() -> OrderLineId.create(""))
                .hasMessage("OrderLineId cannot be empty");
        assertThatThrownBy(() -> OrderLineId.create("   "))
                .hasMessage("OrderLineId cannot be empty");
    }

    @Test
    void createOption_BlankInput_ReturnEmptyOptional() {
        assertThat(OrderLineId.createOption("")).isEmpty();
        assertThat(OrderLineId.createOption("   ")).isEmpty();
    }

    @Test
    void createOption_ValidInput_ReturnPresentOptional() {
        Optional<OrderLineId> opt = OrderLineId.createOption("LINE_999");
        assertThat(opt).isPresent();
        assertThat(opt.get().value()).isEqualTo("LINE_999");
    }

    @Test
    void directNew_BlankValue_ThrowGuardError() {
        assertThatThrownBy(() -> new OrderLineId(""))
                .hasMessage("禁止直接 new OrderLineId，请使用 OrderLineId.create()");
    }
}