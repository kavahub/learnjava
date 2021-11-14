package io.github.kavahub.learnjava;

import java.lang.instrument.Instrumentation;

import lombok.experimental.UtilityClass;

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
