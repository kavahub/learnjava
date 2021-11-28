package io.github.kavahub.learnjava.util;

import java.lang.instrument.Instrumentation;

import lombok.experimental.UtilityClass;

/**
 * {@link Instrumentation} 示例
 * 
 * <p>
 * Instrumentation指的是可以用独立于应用程序之外的代理（agent）程序来监测和协助运行在JVM上的应用程序
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class InstrumentationAgent {
    private static volatile Instrumentation globalInstrumentation;

        public void premain(final String agentArgs, final Instrumentation inst) {
            globalInstrumentation = inst;
        }

        public long getObjectSize(final Object object) {
            if (globalInstrumentation == null) {
                throw new IllegalStateException("Agent not initialized.");
            }
            return globalInstrumentation.getObjectSize(object);
        }
}
