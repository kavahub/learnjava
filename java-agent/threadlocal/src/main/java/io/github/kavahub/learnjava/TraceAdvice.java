package io.github.kavahub.learnjava;

import net.bytebuddy.asm.Advice;

/**
 * TODO
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
        System.out.println("Trace - " + TraceManager.INSTANCE.endSpan().get().toString());
    }
}
