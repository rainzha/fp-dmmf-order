package org.rainzha.dmmf.infrastructure.adapter;

import org.rainzha.dmmf.domain.discriminated.ProductCode;
import org.rainzha.dmmf.domain.port.CheckProductCodeExists;
import org.rainzha.dmmf.domain.valueobj.String50;

/**
 * CheckProductCodeExists 出站端口适配器实现
 */
public class ProductCodeCheckAdapter implements CheckProductCodeExists {

    private final ExternalProductApiClient apiClient;

    public ProductCodeCheckAdapter(ExternalProductApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public ProductCode check(String rawProductCode) {
        // 1. 调用外部服务校验编码是否存在
        boolean isValid = apiClient.isProductCodeValid(rawProductCode);
        if (!isValid) {
            throw new IllegalArgumentException("无效/不存在的商品编码: " + rawProductCode);
        }

        // 2. 原始字符串转为领域值对象 String50（内置长度校验）
        String50 code = String50.create(rawProductCode);

        // 3. 根据编码前缀区分 Widget / Gizmo，调用对应工厂
        if (rawProductCode.startsWith("W")) {
            return ProductCode.ofWidget(code);
        } else if (rawProductCode.startsWith("X")) {
            return ProductCode.ofGizmo(code);
        } else {
            throw new IllegalArgumentException("商品编码类型无法识别，仅支持W(Widget)、X(Gizmo)");
        }
    }

    // 模拟外部第三方商品接口客户端
    public static class ExternalProductApiClient {
        public boolean isProductCodeValid(String rawCode) {
            // 真实项目替换为 HTTP/Feign 远程调用
            return rawCode.startsWith("W") || rawCode.startsWith("X");
        }
    }
}