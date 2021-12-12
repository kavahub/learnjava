package io.github.kavahub.learnjava.plugins;

/**
 * 代理插件
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
public interface Plugin {
        // 名称
        String name();

        // 提供者
        TargetElementProvider[] elementMatchers();

        // 织入类
        Class<?> advice();
}
