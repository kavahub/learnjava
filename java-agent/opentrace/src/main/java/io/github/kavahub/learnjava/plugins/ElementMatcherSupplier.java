package io.github.kavahub.learnjava.plugins;

import java.util.function.Supplier;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

/**
 * TODO
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
public interface ElementMatcherSupplier {
    // 监控方法描述
    Supplier<ElementMatcher<MethodDescription>> methodDescription();

    // 监控类型描述
    Supplier<ElementMatcher<TypeDescription>> typeDescription();
}
