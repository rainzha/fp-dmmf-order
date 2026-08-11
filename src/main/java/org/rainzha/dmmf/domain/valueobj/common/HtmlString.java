package org.rainzha.dmmf.domain.valueobj.common;

/**
 * F# type HtmlString = HtmlString of string
 * 领域专用包装类型，隔离原始字符串，防止普通String与HTML文本混用
 */
public record HtmlString(String rawHtml) {

    /**
     * 紧凑构造器拦截外部直接new，强制使用create()创建实例
     */
    public HtmlString {
        if (rawHtml == null) {
            throw new IllegalArgumentException("请勿直接 new HtmlString，请调用 HtmlString.create()");
        }
    }

    /**
     * 唯一合法构造入口，复刻F#私有单例DU语义
     */
    public static HtmlString create(String rawHtml) {
        // 可按需增加HTML基础校验（标签过滤、长度限制等）
        return new HtmlString(rawHtml);
    }

    /**
     * 等价F#解包：let unwrap (HtmlString s) = s
     */
    public String unwrap() {
        return rawHtml;
    }
}