package io.github.kavahub.learnjava.plugins.jvm;

import io.github.kavahub.learnjava.plugins.TargetElementProvider;
import io.github.kavahub.learnjava.plugins.Plugin;

/**
 * JVM插件，获取JVM信息
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public class JVMPlugin implements Plugin {

    @Override
    public String name() {
        return "JVM";
    }


    @Override
    public Class<?> advice() {
        return JVMAdvice.class;
    }


    @Override
    public TargetElementProvider[] elementMatchers() {
        return new TargetElementProvider[] {new JVMTargetElementProvider()};
    }

    
}
