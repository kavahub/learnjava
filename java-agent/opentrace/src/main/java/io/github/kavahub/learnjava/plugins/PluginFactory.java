package io.github.kavahub.learnjava.plugins;

import java.util.Arrays;
import java.util.List;

import io.github.kavahub.learnjava.plugins.jvm.JVMPlugin;
import io.github.kavahub.learnjava.plugins.trace.TracePlugin;

/**
 * 插件工程，包含所有实现的插件
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public interface PluginFactory {
    static List<Plugin> pluginGroup() {
        return Arrays.asList(new JVMPlugin(), new TracePlugin());
    }
}
