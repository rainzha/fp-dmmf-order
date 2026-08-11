package org.rainzha.dmmf.app.mapper;

import org.rainzha.dmmf.app.dto.UnvalidatedOrder;
import org.rainzha.dmmf.domain.port.CheckAddressExists;
import org.rainzha.dmmf.domain.port.CheckProductCodeExists;
import org.rainzha.dmmf.domain.port.ValidateOrder;
import org.rainzha.dmmf.domain.records.Address;
import org.rainzha.dmmf.domain.records.CustomerInfo;
import org.rainzha.dmmf.domain.records.ValidatedOrder;
import org.rainzha.dmmf.domain.records.ValidatedOrderLine;
import org.rainzha.dmmf.domain.valueobj.OrderId;

import java.util.List;

/**
 * 顶层订单转换映射器，实现 ValidateOrder 端口接口
 * 对应F# let validateOrder 完整实现逻辑
 * 全部墙外适配逻辑，聚合所有toXXX转换函数
 */
public class OrderMapper implements ValidateOrder {

    /**
     * 实现端口校验方法，完整对齐F# validateOrder 逻辑
     */
    @Override
    public ValidatedOrder validate(
            CheckProductCodeExists checkProductCodeExists,
            CheckAddressExists checkAddressExists,
            UnvalidatedOrder unvalidatedOrder
    ) {
        // 1. 订单ID原始字符串转为领域值对象
        OrderId orderId = OrderId.create(unvalidatedOrder.orderId());

        // 2. 转换客户信息
        CustomerInfo customerInfo = CustomerInfoMapper.toCustomerInfo(unvalidatedOrder.customerInfo());

        // 3. 转换收货地址（依赖地址校验出站端口）
        Address shippingAddress = AddressMapper.toAddress(checkAddressExists, unvalidatedOrder.shippingAddress());

        // 4. 批量转换所有订单项
        List<ValidatedOrderLine> validatedLines = unvalidatedOrder.lines().stream()
                .map(line -> ValidatedOrderLineMapper.toValidatedOrderLine(checkProductCodeExists, line))
                .toList();

        // 5. 组装完整合法领域 ValidatedOrder 返回
        return new ValidatedOrder(orderId, customerInfo, shippingAddress, validatedLines);
    }
}