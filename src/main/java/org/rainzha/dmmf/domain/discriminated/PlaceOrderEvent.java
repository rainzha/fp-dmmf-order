package org.rainzha.dmmf.domain.discriminated;

import org.rainzha.dmmf.domain.records.BillableOrderPlaced;
import org.rainzha.dmmf.domain.records.OrderAcknowledgmentSent;
import org.rainzha.dmmf.domain.records.OrderPlaced;

import java.util.function.Function;

/**
 * F# 联合类型：
 * type PlaceOrderEvent =
 * | OrderPlaced of OrderPlaced
 * | BillableOrderPlaced of BillableOrderPlaced
 * | AcknowledgmentSent of OrderAcknowledgmentSent
 * 下单流程产出的全部领域事件总类型
 */
public sealed interface PlaceOrderEvent
        permits PlaceOrderEvent.OrderPlacedEvent,
        PlaceOrderEvent.BillableOrderPlacedEvent,
        PlaceOrderEvent.AcknowledgmentSentEvent {

    // 统一穷尽式 match，和你项目所有 DU 风格保持一致
    <R> R match(
            Function<OrderPlaced, R> onOrderPlaced,
            Function<BillableOrderPlaced, R> onBillableOrderPlaced,
            Function<OrderAcknowledgmentSent, R> onAcknowledgmentSent
    );

    // 分支1：OrderPlaced of OrderPlaced
    record OrderPlacedEvent(OrderPlaced value) implements PlaceOrderEvent {
        @Override
        public <R> R match(
                Function<OrderPlaced, R> onOrderPlaced,
                Function<BillableOrderPlaced, R> onBillableOrderPlaced,
                Function<OrderAcknowledgmentSent, R> onAcknowledgmentSent
        ) {
            return onOrderPlaced.apply(this.value);
        }
    }

    // 分支2：BillableOrderPlaced of BillableOrderPlaced
    record BillableOrderPlacedEvent(BillableOrderPlaced value) implements PlaceOrderEvent {
        @Override
        public <R> R match(
                Function<OrderPlaced, R> onOrderPlaced,
                Function<BillableOrderPlaced, R> onBillableOrderPlaced,
                Function<OrderAcknowledgmentSent, R> onAcknowledgmentSent
        ) {
            return onBillableOrderPlaced.apply(this.value);
        }
    }

    // 分支3：AcknowledgmentSent of OrderAcknowledgmentSent
    record AcknowledgmentSentEvent(OrderAcknowledgmentSent value) implements PlaceOrderEvent {
        @Override
        public <R> R match(
                Function<OrderPlaced, R> onOrderPlaced,
                Function<BillableOrderPlaced, R> onBillableOrderPlaced,
                Function<OrderAcknowledgmentSent, R> onAcknowledgmentSent
        ) {
            return onAcknowledgmentSent.apply(this.value);
        }
    }
}