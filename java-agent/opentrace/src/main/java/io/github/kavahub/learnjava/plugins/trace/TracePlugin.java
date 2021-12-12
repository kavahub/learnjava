package io.github.kavahub.learnjava.plugins.trace;

import io.github.kavahub.learnjava.plugins.TargetElementProvider;
import io.github.kavahub.learnjava.plugins.Plugin;

/**
 * 链路追踪插件
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
    public TargetElementProvider[] elementMatchers() {
        return new TargetElementProvider[] {new TraceTargetElementProvider()};
    }

    
}
