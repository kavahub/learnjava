package io.github.kavahub.learnjava.plugins.trace;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.UUID;

/**
 * 追踪管理器。包含一个追踪栈，记录追踪信息；使用 {@code ThreadLocal} 保存跟踪ID
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public class TraceManager {
    public final static TraceManager INSTANCE = new TraceManager();

    // 追踪栈
    private final ThreadLocal<ArrayDeque<Span>> trace = new ThreadLocal<ArrayDeque<Span>>();
    // 追踪ID
    private final ThreadLocal<String> traceId = new ThreadLocal<String>();

    private TraceManager() {
    }

    private String createTraceId() {
        final String uuid = UUID.randomUUID().toString();
        traceId.set(uuid);
        return uuid;
    }

    public Optional<Span> beginSpan(String name) {
        // 获取追踪ID
        String tid = traceId.get();
        if (tid == null) {
            // 追踪入口，创建ID 和追踪栈
            tid = createTraceId();
            trace.set(new ArrayDeque<Span>());
        }

        ArrayDeque<Span> stack = trace.get();
        final Span span = new Span(tid, name);
        stack.push(span);
        
        return Optional.of(span);
    }

    public Optional<Span> endSpan() {
        ArrayDeque<Span> stack = trace.get();

        if (stack == null || stack.isEmpty()) {
            // 异常情况
            return Optional.empty();
        }

        if (stack.size() == 1) {
            // 追踪出口
            trace.remove();
            traceId.remove();
        }
        
        final Span span = stack.pop();
        span.setEnd(System.currentTimeMillis());
        return Optional.ofNullable(span);
    }


    public Optional<Span> currentSpan() {
        ArrayDeque<Span> stack = trace.get();

        if (stack == null || stack.isEmpty()) {
            // 异常情况
            return Optional.empty();
        }

        return Optional.ofNullable(stack.peek());
    }

}
