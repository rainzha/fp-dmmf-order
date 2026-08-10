package org.rainzha.dmmf.domain.valueobj;

public record OrderId(String value) {
    // 紧凑构造器必须 public，兜底拦截非法参数，防止直接 new 传入无效值
    public OrderId {
        if (value == null || value.isBlank() || value.length() > 50) {
            throw new IllegalArgumentException("请勿直接 new OrderId，请使用 OrderId.create() 构造合法实例");
        }
    }

    // 唯一推荐创建入口，对齐 F# create 函数
    public static OrderId create(String str) {
        if (str == null || str.isBlank()) {
            throw new IllegalArgumentException("OrderId must not be null or empty");
        }
        if (str.length() > 50) {
            throw new IllegalArgumentException("OrderId must not be more than 50 chars");
        }
        return new OrderId(str);
    }

    // 等价F# let value (OrderId str) = str，record自带value()访问器，无需额外方法
}