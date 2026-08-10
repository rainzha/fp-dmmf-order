package org.rainzha.dmmf.domain.valueobj;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderIdTest {

    @Test
    void create_ValidInput_ReturnValidOrderId() {
        String raw = "ORD-20260810-0001";
        OrderId orderId = OrderId.create(raw);
        assertEquals(raw, orderId.value());
    }

    @Test
    void create_NullOrEmpty_ThrowIllegalArg() {
        assertThrows(IllegalArgumentException.class, () -> OrderId.create(null));
        assertThrows(IllegalArgumentException.class, () -> OrderId.create(""));
        assertThrows(IllegalArgumentException.class, () -> OrderId.create("   "));
    }

    @Test
    void create_Over50Char_ThrowIllegalArg() {
        String longId = "X".repeat(51);
        assertThrows(IllegalArgumentException.class, () -> OrderId.create(longId));
    }

    @Test
    void directNew_InvalidValue_Throw() {
        // 兜底校验，防止有人绕过 create 直接 new
        assertThrows(IllegalArgumentException.class, () -> new OrderId(""));
    }
}