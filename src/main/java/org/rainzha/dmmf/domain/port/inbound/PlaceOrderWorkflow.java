package org.rainzha.dmmf.domain.port.inbound;


import org.rainzha.dmmf.app.dto.UnvalidatedOrder;
import org.rainzha.dmmf.domain.discriminated.PlaceOrderEvent;

import java.util.List;

// type PlaceOrderWorkflow = UnvalidatedOrder -> PlaceOrderEvent list
@FunctionalInterface
public interface PlaceOrderWorkflow {
    List<PlaceOrderEvent> run(UnvalidatedOrder unvalidatedOrder);
}