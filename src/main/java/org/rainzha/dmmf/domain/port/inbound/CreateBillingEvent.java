package org.rainzha.dmmf.domain.port.inbound;

import org.rainzha.dmmf.domain.discriminated.OptionResult;
import org.rainzha.dmmf.domain.records.BillableOrderPlaced;
import org.rainzha.dmmf.domain.records.PricedOrder;

/**
 * PricedOrder -> BillableOrderPlaced option
 * 根据计价订单生成账单事件，金额>0才返回Some，否则None
 */
@FunctionalInterface
public interface CreateBillingEvent {
    OptionResult<BillableOrderPlaced> create(PricedOrder placedOrder);
}