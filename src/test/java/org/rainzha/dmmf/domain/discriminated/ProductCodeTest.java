package org.rainzha.dmmf.domain.discriminated;

import org.junit.jupiter.api.Test;
import org.rainzha.dmmf.domain.valueobj.String50;

import static org.assertj.core.api.Assertions.assertThat;

class ProductCodeTest {
    private final String50 testCode = String50.create("SKU-DEMO-001");

    @Test
    void ofWidget_ConstructWidgetVariant_CorrectType() {
        ProductCode productCode = ProductCode.ofWidget(testCode);
        assertThat(productCode).isInstanceOf(ProductCode.Widget.class);
        assertThat(((ProductCode.Widget) productCode).code()).isEqualTo(testCode);
    }

    @Test
    void ofGizmo_ConstructGizmoVariant_CorrectType() {
        ProductCode productCode = ProductCode.ofGizmo(testCode);
        assertThat(productCode).isInstanceOf(ProductCode.Gizmo.class);
        assertThat(((ProductCode.Gizmo) productCode).code()).isEqualTo(testCode);
    }

    @Test
    void match_WidgetVariant_ReturnWidgetText() {
        ProductCode widget = ProductCode.ofWidget(testCode);
        String label = widget.match(
                s -> "Widget[" + s.value() + "]",
                s -> "Gizmo[" + s.value() + "]"
        );
        assertThat(label).isEqualTo("Widget[SKU-DEMO-001]");
    }

    @Test
    void match_GizmoVariant_ReturnGizmoText() {
        ProductCode gizmo = ProductCode.ofGizmo(testCode);
        String label = gizmo.match(
                s -> "Widget[" + s.value() + "]",
                s -> "Gizmo[" + s.value() + "]"
        );
        assertThat(label).isEqualTo("Gizmo[SKU-DEMO-001]");
    }

    @Test
    void matchAction_WidgetVariant_ExecuteWidgetConsumer() {
        ProductCode widget = ProductCode.ofWidget(testCode);
        StringBuilder buffer = new StringBuilder();

        widget.matchAction(
                s -> buffer.append("WIDGET:").append(s.value()),
                s -> buffer.append("GIZMO:").append(s.value())
        );

        assertThat(buffer.toString()).isEqualTo("WIDGET:SKU-DEMO-001");
    }

    @Test
    void matchAction_GizmoVariant_ExecuteGizmoConsumer() {
        ProductCode gizmo = ProductCode.ofGizmo(testCode);
        StringBuilder buffer = new StringBuilder();

        gizmo.matchAction(
                s -> buffer.append("WIDGET:").append(s.value()),
                s -> buffer.append("GIZMO:").append(s.value())
        );

        assertThat(buffer.toString()).isEqualTo("GIZMO:SKU-DEMO-001");
    }
}