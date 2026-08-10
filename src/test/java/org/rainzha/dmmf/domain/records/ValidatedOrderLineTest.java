package org.rainzha.dmmf.domain.records;

import org.junit.jupiter.api.Test;
import org.rainzha.dmmf.domain.discriminated.OrderQuantity;
import org.rainzha.dmmf.domain.discriminated.ProductCode;
import org.rainzha.dmmf.domain.valueobj.OrderLineId;
import org.rainzha.dmmf.domain.valueobj.String50;
import org.rainzha.dmmf.domain.valueobj.UnitQuantity;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ValidatedOrderLineTest {
    // 分层构建：先基础值对象，再DU求和类型
    private final OrderLineId lineId = OrderLineId.create("LINE-001");
    private final String50 widgetCode = String50.create("WDG-1001");
    private final ProductCode product = ProductCode.ofWidget(widgetCode);
    // 修复点：先构建UnitQuantity，再用ofUnit生成OrderQuantity
    private final UnitQuantity unitQty = UnitQuantity.create(5);
    private final OrderQuantity quantity = OrderQuantity.ofUnit(unitQty);

    @Test
    void build_WithValidDomainTypes_FieldsMatch() {
        ValidatedOrderLine line = new ValidatedOrderLine(lineId, product, quantity);

        assertThat(line.orderLineId()).isEqualTo(lineId);
        assertThat(line.productCode()).isEqualTo(product);
        assertThat(line.quantity()).isEqualTo(quantity);
    }

    @Test
    void equals_SameDomainValues_InstancesEqual() {
        OrderLineId lineId2 = OrderLineId.create("LINE-001");
        String50 widgetCode2 = String50.create("WDG-1001");
        ProductCode product2 = ProductCode.ofWidget(widgetCode2);
        UnitQuantity unitQty2 = UnitQuantity.create(5);
        OrderQuantity quantity2 = OrderQuantity.ofUnit(unitQty2);

        ValidatedOrderLine l1 = new ValidatedOrderLine(lineId, product, quantity);
        ValidatedOrderLine l2 = new ValidatedOrderLine(lineId2, product2, quantity2);

        // 校验订单行ID相等
        assertThat(l1.orderLineId()).isEqualTo(l2.orderLineId());

        // 提取ProductCode内部编码并校验相等（删除lambda冗余类型）
        String50 code1 = l1.productCode().match(
                widget -> widget,
                gizmo -> gizmo
        );
        String50 code2 = l2.productCode().match(
                widget -> widget,
                gizmo -> gizmo
        );
        assertThat(code1).isEqualTo(code2);

        // 统一转为BigDecimal，解决isEqualByComparingTo找不到方法的问题
        BigDecimal num1 = l1.quantity().match(
                unit -> BigDecimal.valueOf(unit.value()),
                kg -> kg.value()
        );
        BigDecimal num2 = l2.quantity().match(
                unit -> BigDecimal.valueOf(unit.value()),
                kg -> kg.value()
        );
        assertThat(num1).isEqualByComparingTo(num2);
    }
}