package org.rainzha.dmmf.domain.discriminated;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * F# option 通用容器 Some<T> / None
 * 用于表达“有值/无值”，对应 AcknowledgeOrder 返回 OrderAcknowledgmentSent option
 */
public sealed interface OptionResult<T> permits OptionResult.Some, OptionResult.None {
    // 修改：R 统一作为匹配返回类型，两个分支都支持返回 R
    <R> R match(Function<T, R> someCase, Supplier<R> noneCase);

    record Some<T>(T value) implements OptionResult<T> {
        @Override
        public <R> R match(Function<T, R> someCase, Supplier<R> noneCase) {
            return someCase.apply(value);
        }
    }

    OptionResult<?> NONE = new None<>();

    record None<T>() implements OptionResult<T> {
        @Override
        public <R> R match(Function<T, R> someCase, Supplier<R> noneCase) {
            return noneCase.get();
        }
    }

    static <T> OptionResult<T> some(T value) {
        return new Some<>(value);
    }

    @SuppressWarnings("unchecked")
    static <T> OptionResult<T> none() {
        return (OptionResult<T>) NONE;
    }
}