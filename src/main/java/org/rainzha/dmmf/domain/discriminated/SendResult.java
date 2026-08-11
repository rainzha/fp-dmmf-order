package org.rainzha.dmmf.domain.discriminated;

/**
 * F# 区分联合类型：type SendResult = | Sent | NotSent
 * 邮件发送结果枚举式代数类型
 */
public sealed interface SendResult permits SendResult.Sent, SendResult.NotSent {

    // 模式匹配统一接口
    <T> T match(Runnable sentCase, Runnable notSentCase);

    // 成功分支
    record Sent() implements SendResult {
        @Override
        public <T> T match(Runnable sentCase, Runnable notSentCase) {
            sentCase.run();
            return null;
        }
    }

    // 失败分支
    record NotSent() implements SendResult {
        @Override
        public <T> T match(Runnable sentCase, Runnable notSentCase) {
            notSentCase.run();
            return null;
        }
    }

    // 全局单例，复用实例避免重复创建
    SendResult SENT = new Sent();
    SendResult NOT_SENT = new NotSent();
}