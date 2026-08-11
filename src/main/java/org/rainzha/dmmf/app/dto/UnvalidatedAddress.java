package org.rainzha.dmmf.app.dto;

/**
 * 外部原始未校验地址DTO，承载接口原始字符串数据，无领域强校验
 */
public record UnvalidatedAddress(
        String street,
        String city,
        String state,
        String zipCode
) {
}