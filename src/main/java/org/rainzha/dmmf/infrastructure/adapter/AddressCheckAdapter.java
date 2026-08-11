package org.rainzha.dmmf.infrastructure.adapter;

import org.rainzha.dmmf.app.dto.UnvalidatedAddress;
import org.rainzha.dmmf.domain.port.CheckAddressExists;
import org.rainzha.dmmf.domain.port.model.CheckedAddress;
import org.rainzha.dmmf.domain.port.model.CheckedAddressData;

/**
 * 外部地址校验服务的适配器实现（Port接口的墙外实现）
 * 封装HTTP调用第三方地址校验接口
 */
public class AddressCheckAdapter implements CheckAddressExists {
    @Override
    public CheckedAddress check(UnvalidatedAddress unvalidatedAddress) {
        // 1. HTTP调用外部地址校验API
        // 2. 第三方返回JSON映射为CheckedAddressData
        // 3. 包装为CheckedAddress返回给domain层
        CheckedAddressData data = new CheckedAddressData(
                unvalidatedAddress.street(),
                unvalidatedAddress.city(),
                "",
                "",
                unvalidatedAddress.state(),
                unvalidatedAddress.zipCode()
        );
        return new CheckedAddress(data);
    }
}