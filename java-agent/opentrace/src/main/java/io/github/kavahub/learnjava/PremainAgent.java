package io.github.kavahub.learnjava;

import java.lang.instrument.Instrumentation;
import java.util.List;

import io.github.kavahub.learnjava.plugins.TargetElementProvider;
import io.github.kavahub.learnjava.plugins.Plugin;
import io.github.kavahub.learnjava.plugins.PluginFactory;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.utility.JavaModule;

/**
 * 代理入口
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
@Slf4j
public class PremainAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        log.info("Agent called - {}", PremainAgent.class.getName());

        AgentBuilder agentBuilder = new AgentBuilder.Default();

        // 扫描所有的插件
        List<Plugin> plugins = PluginFactory.pluginGroup();
        for (Plugin plugin : plugins) {
            // 需要处理的类及方法
            TargetElementProvider[] providers = plugin.elementMatchers();
            for (TargetElementProvider provider : providers) {
                AgentBuilder.Transformer transformer = (builder, typeDescription, classLoader, javaModule) -> {
                    builder = builder.visit(Advice.to(plugin.advice()).on(provider.methodDescription()));
                    return builder;
                };
                agentBuilder = agentBuilder.type(provider.typeDescription()).transform(transformer);
            }

        }

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

        agentBuilder.with(listener).installOn(inst);
    }
}
