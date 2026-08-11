package org.rainzha.dmmf.domain.discriminated;

import java.util.function.Supplier;

/**
 * F# 区分联合类型：type SendResult = | Sent | NotSent
 * 邮件发送结果枚举式代数类型
 */
public sealed interface SendResult permits SendResult.Sent, SendResult.NotSent {

    // 修复：使用Supplier<T>，分支可以返回任意类型
    <T> T match(Supplier<T> sentCase, Supplier<T> notSentCase);

    // 成功分支
    record Sent() implements SendResult {
        @Override
        public <T> T match(Supplier<T> sentCase, Supplier<T> notSentCase) {
            return sentCase.get();
        }
    }

    // 失败分支
    record NotSent() implements SendResult {
        @Override
        public <T> T match(Supplier<T> sentCase, Supplier<T> notSentCase) {
            return notSentCase.get();
        }
    }

    // 全局单例，复用实例避免重复创建
    SendResult SENT = new Sent();
    SendResult NOT_SENT = new NotSent();
}