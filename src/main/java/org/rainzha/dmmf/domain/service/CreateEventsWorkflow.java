package org.rainzha.dmmf.domain.service;

import org.rainzha.dmmf.domain.discriminated.OptionResult;
import org.rainzha.dmmf.domain.discriminated.PlaceOrderEvent;
import org.rainzha.dmmf.domain.port.inbound.CreateBillingEvent;
import org.rainzha.dmmf.domain.port.inbound.CreateEvents;
import org.rainzha.dmmf.domain.records.BillableOrderPlaced;
import org.rainzha.dmmf.domain.records.OrderAcknowledgmentSent;
import org.rainzha.dmmf.domain.records.OrderPlaced;
import org.rainzha.dmmf.domain.records.PricedOrder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 完全复刻F# createEvents，不新增OptionResult.map，全部用match实现Option映射
 */
public class CreateEventsWorkflow implements CreateEvents {

    private final CreateBillingEvent createBillingEvent;

    public CreateEventsWorkflow(CreateBillingEvent createBillingEvent) {
        this.createBillingEvent = createBillingEvent;
    }

    /**
     * 等价F# let listOfOption (opt: 'T option) : 'T list
     */
    private static <T> List<T> listOfOption(OptionResult<T> opt) {
        return opt.match(
                value -> List.of(value),
                List::of
        );
    }

    @Override
    public List<PlaceOrderEvent> create(
            PricedOrder pricedOrder,
            OptionResult<OrderAcknowledgmentSent> acknowledgmentEventOpt
    ) {
        // 1. events1: pricedOrder |> PlaceOrderEvent.OrderPlaced |> List.singleton
        OrderPlaced rawOrderPlaced = new OrderPlaced(pricedOrder);
        List<PlaceOrderEvent> events1 = List.of(new PlaceOrderEvent.OrderPlacedEvent(rawOrderPlaced));

        // 2. events2: acknowledgmentEventOpt |> Option.map PlaceOrderEvent.AcknowledgmentSent |> listOfOption
        // 用match手动实现Option.map逻辑，不新增map方法
        OptionResult<PlaceOrderEvent> ackEventWrapped = acknowledgmentEventOpt.match(
                ackSent -> OptionResult.some(new PlaceOrderEvent.AcknowledgmentSentEvent(ackSent)),
                OptionResult::none
        );
        List<PlaceOrderEvent> events2 = listOfOption(ackEventWrapped);

        // 3. events3: pricedOrder |> createBillingEvent |> Option.map PlaceOrderEvent.BillableOrderPlaced |> listOfOption
        OptionResult<BillableOrderPlaced> billOpt = createBillingEvent.create(pricedOrder);
        OptionResult<PlaceOrderEvent> billEventWrapped = billOpt.match(
                bill -> OptionResult.some(new PlaceOrderEvent.BillableOrderPlacedEvent(bill)),
                OptionResult::none
        );
        List<PlaceOrderEvent> events3 = listOfOption(billEventWrapped);

        // 等价 F# [ yield! events1; yield! events2; yield! events3 ]
        return Stream.of(events1, events2, events3)
                .flatMap(List::stream)
                .collect(Collectors.toUnmodifiableList());
    }
}