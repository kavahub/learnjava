package io.github.kavahub.learnjava.plugins.trace;

import java.util.function.Supplier;

import io.github.kavahub.learnjava.plugins.ElementMatcherSupplier;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * TODO
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
public class TargetClassElementMatcherSupplier implements ElementMatcherSupplier {

    public Supplier<ElementMatcher<MethodDescription>> methodDescription() {
        return new Supplier<ElementMatcher<MethodDescription>>() {

            @Override
            public ElementMatcher<MethodDescription> get() {
                return ElementMatchers.isMethod()
                // 包含所有的方法
                .and(ElementMatchers.any())
                // 不包含 main 方法
                .and(ElementMatchers.not(ElementMatchers.nameStartsWith("main")));
            }
        };
    }

    public Supplier<ElementMatcher<TypeDescription>> typeDescription() {
        return new Supplier<ElementMatcher<TypeDescription>>() {

            @Override
            public ElementMatcher<TypeDescription> get() {
                return ElementMatchers.named("io.github.kavahub.learnjava.TargetClass");
            }
        };
    }
}
