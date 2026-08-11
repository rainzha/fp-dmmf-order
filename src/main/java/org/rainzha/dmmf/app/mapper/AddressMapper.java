package org.rainzha.dmmf.app.mapper;

import org.rainzha.dmmf.app.dto.UnvalidatedAddress;
import org.rainzha.dmmf.domain.port.CheckAddressExists;
import org.rainzha.dmmf.domain.port.model.CheckedAddress;
import org.rainzha.dmmf.domain.port.model.CheckedAddressData;
import org.rainzha.dmmf.domain.records.Address;
import org.rainzha.dmmf.domain.valueobj.String50;
import org.rainzha.dmmf.domain.valueobj.ZipCode;

import java.util.Optional;

/**
 * 墙外映射器：未校验地址DTO + 外部地址校验端口 → 领域合法Address
 * 对应F# toAddress 转换函数
 */
public class AddressMapper {

    /**
     * F#: let toAddress (checkAddressExists: CheckAddressExists) (unvalidatedAddress: UnvalidatedAddress) : Address
     */
    public static Address toAddress(
            CheckAddressExists checkAddressExists,
            UnvalidatedAddress unvalidatedAddress
    ) {
        // 1. 调用外部地址校验服务端口，获取包装后的CheckedAddress
        CheckedAddress checkedAddress = checkAddressExists.check(unvalidatedAddress);
        // 解包单值DU CheckedAddress -> CheckedAddressData
        CheckedAddressData data = checkedAddress.data();

        // 2. 必选字段：原始字符串转为强校验值对象，非法直接抛异常
        String50 addressLine1 = String50.create(data.addressLine1());
        String50 city = String50.create(data.city());
        ZipCode zipCode = ZipCode.create(data.zipCode());

        // 3. 可选字段：createOption 对应F# |> String50.createOption，空字符串返回Optional.empty
        Optional<String50> addressLine2 = String50.createOption(data.addressLine2());
        Optional<String50> addressLine3 = String50.createOption(data.addressLine3());
        Optional<String50> addressLine4 = String50.createOption(data.addressLine4());

        // 4. 组装领域Address记录并返回
        return new Address(
                addressLine1,
                addressLine2,
                addressLine3,
                addressLine4,
                city,
                zipCode
        );
    }
}