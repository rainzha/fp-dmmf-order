package org.rainzha.dmmf.domain.discriminated;

import java.util.function.Function;

/**
 * F# option 通用容器 Some<T> / None
 * 用于表达“有值/无值”，对应 AcknowledgeOrder 返回 OrderAcknowledgmentSent option
 */
public sealed interface OptionResult<T> permits OptionResult.Some, OptionResult.None {

    <R> R match(Function<T, R> someCase, Runnable noneCase);

    // 存在值分支
    record Some<T>(T value) implements OptionResult<T> {
        @Override
        public <R> R match(Function<T, R> someCase, Runnable noneCase) {
            return someCase.apply(value);
        }
    }

    // 空值单例
    OptionResult<?> NONE = new None<>();

    record None<T>() implements OptionResult<T> {
        @Override
        public <R> R match(Function<T, R> someCase, Runnable noneCase) {
            noneCase.run();
            return null;
        }
    }

    // 静态构造快捷方法
    static <T> OptionResult<T> some(T value) {
        return new Some<>(value);
    }

    @SuppressWarnings("unchecked")
    static <T> OptionResult<T> none() {
        return (OptionResult<T>) NONE;
    }
}