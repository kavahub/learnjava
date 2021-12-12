package io.github.kavahub.learnjava;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

/**
 * Byte Buddy 实现
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
@Slf4j
public class TransformerWithByteBuddy implements Transformer {
    private final static String TARGET_CLASS = "io.github.kavahub.learnjava.TargetClass";

    @Override
    public void transform(String args, Instrumentation inst) {
        AgentBuilder.Transformer transformer = (builder, typeDescription, classLoader, javaModule) -> {
            return builder
                    // 拦截任意方法
                    .method(ElementMatchers.any())
                    // 委托
                    .intercept(MethodDelegation.to(ElapseOfTimeWriter.class));
        };
    
        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
    
            @Override
            public void onDiscovery(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
            }
    
            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module,
                    boolean loaded, DynamicType dynamicType) {
                log.info("onTransformation - {}", typeDescription);
            }
    
            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module,
                    boolean loaded) {
            }
    
            @Override
            public void onError(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded,
                    Throwable throwable) {
            }
    
            @Override
            public void onComplete(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
            }
        };

        new AgentBuilder.Default()
                // 指定需要拦截的类
                .type(ElementMatchers.named(TARGET_CLASS)) 
                .transform(transformer)
                .with(listener)
                .installOn(inst);

    }

    public static class ElapseOfTimeWriter {
        @RuntimeType
        public static Object wirte(@Origin Method method, @SuperCall Callable<?> callable) throws Exception {
            final StopWatch.Clazz stopWatch = new StopWatch.Clazz(method.getName());
            stopWatch.start();
            try {
                // 原有函数执行
                return callable.call();
            } finally {
                stopWatch.end();
            }
        }
    }

}
