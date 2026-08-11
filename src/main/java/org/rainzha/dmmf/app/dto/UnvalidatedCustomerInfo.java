package org.rainzha.dmmf.app.dto;

/**
 * 外部原始输入DTO，未做任何领域校验，承载不可信原始接口数据
 * 存放于app.dto（Port边界墙外，不属于domain内核）
 */
public record UnvalidatedCustomerInfo(
        String firstName,
        String lastName,
        String emailAddress
) {
}