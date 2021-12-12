package io.github.kavahub.learnjava.plugins.trace;

import lombok.Data;

/**
 * 操作定义
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
@Data
public class Span {
    private final String traceId;

    // 操作名称
    private final String name;
    // 开始时间
    private final long start;
    // 结束时间
    private long end;

    public Span(String traceId, String name) {
        this.traceId = traceId;
        this.name = name;
        this.start = System.currentTimeMillis();
    }

}
