package io.github.kavahub.learnjava.plugins;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

/**
 * 目标提供者，需要处理的类及方法
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
public interface TargetElementProvider {
    // 监控方法描述
    ElementMatcher<MethodDescription> methodDescription();

    // 监控类型描述
    ElementMatcher<TypeDescription> typeDescription();
}
