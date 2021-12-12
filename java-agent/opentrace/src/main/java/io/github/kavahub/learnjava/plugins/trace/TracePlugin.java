package io.github.kavahub.learnjava.plugins.trace;

import io.github.kavahub.learnjava.plugins.ElementMatcherSupplier;
import io.github.kavahub.learnjava.plugins.Plugin;

/**
 * TODO
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public class TracePlugin implements Plugin {

    @Override
    public String name() {
        return "TRACE";
    }

    
    @Override
    public Class<?> advice() {
        return TraceAdvice.class;
    }


    @Override
    public ElementMatcherSupplier[] elementMatchers() {
        return new ElementMatcherSupplier[] {new TargetClassElementMatcherSupplier()};
    }

    
}
