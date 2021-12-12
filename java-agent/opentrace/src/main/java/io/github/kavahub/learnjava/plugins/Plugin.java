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

        ElementMatcherSupplier[] elementMatchers();

        // 拦截器类
        Class<?> advice();
}
