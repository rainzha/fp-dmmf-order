package org.rainzha.dmmf.app.mapper;

import org.rainzha.dmmf.domain.discriminated.OrderQuantity;
import org.rainzha.dmmf.domain.discriminated.ProductCode;
import org.rainzha.dmmf.domain.port.inbound.ToPricedOrderLine;
import org.rainzha.dmmf.domain.port.outbound.GetProductPrice;
import org.rainzha.dmmf.domain.records.PricedOrderLine;
import org.rainzha.dmmf.domain.records.ValidatedOrderLine;
import org.rainzha.dmmf.domain.valueobj.Price;

/**
 * 对应F# let toPricedOrderLine 完整实现逻辑
 * 实现入站端口 ToPricedOrderLine，聚合价格查询出站端口完成定价计算
 */
public class PricedOrderLineMapper implements ToPricedOrderLine {

    @Override
    public PricedOrderLine convert(GetProductPrice getProductPrice, ValidatedOrderLine validatedLine) {
        // 1. 根据商品编码调用外部价格端口获取单价
        ProductCode productCode = validatedLine.productCode();
        Price unitPrice = getProductPrice.getPrice(productCode);

        // 2. 数量 × 单价，计算行总价
        OrderQuantity quantity = validatedLine.quantity();
        Price linePrice = Price.multiply(quantity, unitPrice);

        // 3. 组装定价订单行返回
        return new PricedOrderLine(
                validatedLine.orderLineId(),
                productCode,
                quantity,
                linePrice
        );
    }
}