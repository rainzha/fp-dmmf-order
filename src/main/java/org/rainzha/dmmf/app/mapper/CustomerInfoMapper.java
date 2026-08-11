package org.rainzha.dmmf.app.mapper;

import org.rainzha.dmmf.app.dto.UnvalidatedCustomerInfo;
import org.rainzha.dmmf.domain.records.CustomerInfo;
import org.rainzha.dmmf.domain.records.PersonalName;
import org.rainzha.dmmf.domain.valueobj.EmailAddress;
import org.rainzha.dmmf.domain.valueobj.String50;

/**
 * 墙外映射器：外部未校验客户信息DTO → 领域合法CustomerInfo
 * 对应F# toCustomerInfo 转换函数
 * 所有原始字符串校验、值对象构造均在此完成，异常向上抛出
 */
public class CustomerInfoMapper {

    /**
     * F#: let toCustomerInfo (customer: UnvalidatedCustomerInfo) : CustomerInfo
     */
    public static CustomerInfo toCustomerInfo(UnvalidatedCustomerInfo customer) {
        // 原始字符串通过值对象智能构造器校验，非法直接抛异常
        String50 firstName = String50.create(customer.firstName());
        String50 lastName = String50.create(customer.lastName());
        EmailAddress emailAddress = EmailAddress.create(customer.emailAddress());

        // 构建 PersonalName 领域记录
        PersonalName name = new PersonalName(firstName, lastName);

        // 组装并返回合法领域 CustomerInfo
        return new CustomerInfo(name, emailAddress);
    }
}