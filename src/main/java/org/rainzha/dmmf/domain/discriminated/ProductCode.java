package org.rainzha.dmmf.domain.discriminated;

import org.rainzha.dmmf.domain.valueobj.String50;

import java.util.function.Consumer;
import java.util.function.Function;

public sealed abstract class ProductCode permits ProductCode.Widget, ProductCode.Gizmo {
    private static final String UNKNOWN_TYPE_ERR = "Unknown ProductCode variant type";

    public <T> T match(
            Function<String50, T> widgetCase,
            Function<String50, T> gizmoCase
    ) {
        if (this instanceof Widget w) {
            return widgetCase.apply(w.code());
        } else if (this instanceof Gizmo g) {
            return gizmoCase.apply(g.code());
        }
        throw new IllegalStateException(UNKNOWN_TYPE_ERR);
    }

    public void matchAction(
            Consumer<String50> widgetCase,
            Consumer<String50> gizmoCase
    ) {
        match(
                code -> {
                    widgetCase.accept(code);
                    return null;
                },
                code -> {
                    gizmoCase.accept(code);
                    return null;
                }
        );
    }

    public static ProductCode ofWidget(String50 code) {
        return new Widget(code);
    }

    public static ProductCode ofGizmo(String50 code) {
        return new Gizmo(code);
    }

    public static final class Widget extends ProductCode {
        private final String50 code;

        private Widget(String50 code) {
            this.code = code;
        }

        public String50 code() {
            return code;
        }
    }

    public static final class Gizmo extends ProductCode {
        private final String50 code;

        private Gizmo(String50 code) {
            this.code = code;
        }

        public String50 code() {
            return code;
        }
    }
}