package org.rainzha.dmmf.domain.discriminated;

import org.junit.jupiter.api.Test;
import org.rainzha.dmmf.domain.valueobj.KilogramQuantity;
import org.rainzha.dmmf.domain.valueobj.UnitQuantity;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

class OrderQuantityTest {

    private final UnitQuantity unit5 = UnitQuantity.create(5);
    private final KilogramQuantity kg20 = KilogramQuantity.create("20.0");

    @Test
    void ofUnit_BuildUnitVariant_Success() {
        OrderQuantity oq = OrderQuantity.ofUnit(unit5);
        assertThat(oq).isNotNull();
    }

    @Test
    void ofKilogram_BuildKilogramVariant_Success() {
        OrderQuantity oq = OrderQuantity.ofKilogram(kg20);
        assertThat(oq).isNotNull();
    }

    @Test
    void match_WithReturn_UnitBranchExtractCorrectValue() {
        OrderQuantity unitOq = OrderQuantity.ofUnit(unit5);
        String desc = unitOq.match(
                unit -> "Quantity:" + unit.value(),
                kg -> "Weight:" + kg.value()
        );
        assertThat(desc).isEqualTo("Quantity:5");
    }

    @Test
    void match_WithReturn_KilogramBranchExtractCorrectValue() {
        OrderQuantity kgOq = OrderQuantity.ofKilogram(kg20);
        String desc = kgOq.match(
                unit -> "Quantity:" + unit.value(),
                kg -> "Weight:" + kg.value()
        );
        assertThat(desc).isEqualTo("Weight:20.0");
    }

    @Test
    void matchAction_VoidConsumer_UnitBranchCaptureValue() {
        OrderQuantity unitOq = OrderQuantity.ofUnit(unit5);
        final int[] capture = {0};
        unitOq.matchAction(
                unit -> capture[0] = unit.value(),
                kg -> capture[0] = -1
        );
        assertThat(capture[0]).isEqualTo(5);
    }

    @Test
    void matchAction_VoidConsumer_KilogramBranchCaptureValue() {
        OrderQuantity kgOq = OrderQuantity.ofKilogram(kg20);
        final BigDecimal[] capture = {BigDecimal.ZERO};
        kgOq.matchAction(
                unit -> capture[0] = BigDecimal.valueOf(-1),
                kg -> capture[0] = kg.value()
        );
        assertThat(capture[0]).isEqualByComparingTo(new BigDecimal("20.0"));
    }

    @Test
    void matchAction_ExecuteWithoutException() {
        OrderQuantity unitOq = OrderQuantity.ofUnit(unit5);
        assertThatNoException().isThrownBy(() -> unitOq.matchAction(u -> {
        }, k -> {
        }));
    }
}