package org.rainzha.dmmf.domain.records;

import org.rainzha.dmmf.domain.valueobj.OrderId;

import java.util.List;

public record ValidatedOrder(
        OrderId orderId,
        CustomerInfo customerInfo,
        Address shippingAddress,
        List<ValidatedOrderLine> lines
) {
}