package io.github.kavahub.learnjava.plugins.trace;

import net.bytebuddy.asm.Advice;

/**
 * 链路追踪织入
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public class TraceAdvice {
    @Advice.OnMethodEnter()
    public static void enter(@Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName) {
        TraceManager.INSTANCE.beginSpan(className + "." + methodName);
    }

    @Advice.OnMethodExit()
    public static void exit(@Advice.Origin("#t") String className, @Advice.Origin("#m") String methodName) {
        final Span span = TraceManager.INSTANCE.endSpan().get();
        System.out.println("链路追踪(MQ) - " + span.toString());
    }
}
