package io.github.kavahub.learnjava.plugins.jvm;

import io.github.kavahub.learnjava.plugins.ElementMatcherSupplier;
import io.github.kavahub.learnjava.plugins.Plugin;

/**
 * TODO
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
    public ElementMatcherSupplier[] elementMatchers() {
        return new ElementMatcherSupplier[] {new TargetClassElementMatcherSupplier()};
    }

    
}
