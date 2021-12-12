package io.github.kavahub.learnjava;

import java.lang.instrument.Instrumentation;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

/**
 * TODO
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
@Slf4j
public class PremainAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        log.info("Agent called - {}", PremainAgent.class.getName());

        AgentBuilder.Transformer transformer = (builder, typeDescription, classLoader, javaModule) -> {
            builder = builder.visit(
                    Advice.to(TraceAdvice.class)
                            .on(ElementMatchers.any()));
            return builder;
        };

        // 监听
        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            @Override
            public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader,
                    JavaModule javaModule, boolean b, DynamicType dynamicType) {
                System.out.println("onTransformation - " + typeDescription);
            }

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule,
                    boolean b) {

            }

            @Override
            public void onError(String s, ClassLoader classLoader, JavaModule javaModule, boolean b,
                    Throwable throwable) {

            }

            @Override
            public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

        };

        new AgentBuilder.Default()
                // 指定需要拦截的类
                .type(ElementMatchers.nameStartsWith("io.github.kavahub.learnjava.TargetClass"))
                .transform(transformer)
                .with(listener)
                .installOn(inst);

    }
}
