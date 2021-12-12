package io.github.kavahub.learnjava.plugins.jvm;

import io.github.kavahub.learnjava.plugins.TargetElementProvider;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * 需要监控的类及方法
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
public class JVMTargetElementProvider implements TargetElementProvider {

    public ElementMatcher<MethodDescription> methodDescription() {
        return ElementMatchers.isMethod()
                // 包含所有的方法
                .and(ElementMatchers.any())
                // 不包含 main 方法
                .and(ElementMatchers.not(ElementMatchers.nameStartsWith("main")));
    }

    public ElementMatcher<TypeDescription> typeDescription() {
        return ElementMatchers.named("io.github.kavahub.learnjava.TargetClass");
    }
}
