package org.rainzha.dmmf.domain.discriminated;

import org.rainzha.dmmf.domain.valueobj.KilogramQuantity;
import org.rainzha.dmmf.domain.valueobj.UnitQuantity;

import java.util.function.Consumer;
import java.util.function.Function;

// 密封抽象父类，模拟可区分联合
public sealed abstract class OrderQuantity permits OrderQuantity.UnitQty, OrderQuantity.KgQty {

    // 分支匹配（模拟F# pattern matching）
    public <T> T match(
            Function<UnitQuantity, T> unitCase,
            Function<KilogramQuantity, T> kgCase
    ) {
        if (this instanceof UnitQty u) {
            return unitCase.apply(u.qty());
        } else if (this instanceof KgQty k) {
            return kgCase.apply(k.qty());
        }
        throw new IllegalStateException("未知订单数量类型");
    }

    // 无返回值分支匹配
    public void matchAction(
            Consumer<UnitQuantity> unitCase,
            Consumer<KilogramQuantity> kgCase
    ) {
        match(u -> {
            unitCase.accept(u);
            return null;
        }, k -> {
            kgCase.accept(k);
            return null;
        });
    }

    // 工厂静态方法
    public static OrderQuantity ofUnit(UnitQuantity unitQty) {
        return new UnitQty(unitQty);
    }

    public static OrderQuantity ofKilogram(KilogramQuantity kgQty) {
        return new KgQty(kgQty);
    }

    // 分支1：Unit 包装
    public static final class UnitQty extends OrderQuantity {
        private final UnitQuantity qty;

        private UnitQty(UnitQuantity qty) {
            this.qty = qty;
        }

        // 公共读取方法
        public UnitQuantity qty() {
            return qty;
        }
    }

    // 分支2：Kilogram 包装（补上 qty()）
    public static final class KgQty extends OrderQuantity {
        private final KilogramQuantity qty;

        private KgQty(KilogramQuantity qty) {
            this.qty = qty;
        }

        // 公共读取方法
        public KilogramQuantity qty() {
            return qty;
        }
    }
}