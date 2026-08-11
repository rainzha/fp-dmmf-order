package org.rainzha.dmmf.domain.port.model;

/**
 * 外部地址校验服务返回的原始结构化地址数据
 * 属于端口契约模型，存放于domain.port.model（墙内）
 */
public record CheckedAddressData(
        String addressLine1,
        String addressLine2,
        String addressLine3,
        String addressLine4,
        String city,
        String zipCode
) {
}