package org.rainzha.dmmf.domain.service;

import org.rainzha.dmmf.domain.discriminated.OptionResult;
import org.rainzha.dmmf.domain.port.inbound.CreateBillingEvent;
import org.rainzha.dmmf.domain.records.BillableOrderPlaced;
import org.rainzha.dmmf.domain.records.PricedOrder;
import org.rainzha.dmmf.domain.valueobj.BillingAmount;
import org.rainzha.dmmf.domain.valueobj.Price;

import java.math.BigDecimal;

/**
 * F# 原版：
 * // PricedOrder -> BillableOrderPlaced option
 * let createBillingEvent (placedOrder:PricedOrder) : BillableOrderPlaced option =
 * 领域纯函数：根据计价订单生成账单事件，仅金额大于0时返回事件
 * 归属：domain.service 墙内领域业务工作流，不放在app.mapper
 */
public class CreateBillingEventWorkflow implements CreateBillingEvent {

    @Override
    public OptionResult<BillableOrderPlaced> create(PricedOrder placedOrder) {
        // let billingAmount = placedOrder.AmountToBill |> BillingAmount.value |> Price.value
        BillingAmount billingAmountObj = placedOrder.amountToBill();
        Price priceObj = billingAmountObj.value();
        BigDecimal billingAmount = priceObj.value();

        // if billingAmount > 0M then Some 账单事件 else None
        if (billingAmount.compareTo(BigDecimal.ZERO) > 0) {
            BillableOrderPlaced billableOrder = new BillableOrderPlaced(
                    placedOrder.orderId(),
                    placedOrder.billingAddress(),
                    placedOrder.amountToBill()
            );
            return OptionResult.some(billableOrder);
        }
        return OptionResult.none();
    }
}