package io.github.kavahub.learnjava;

import java.lang.instrument.Instrumentation;

/**
 * 转换接口
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
public interface Transformer {
    public void transform(String args, Instrumentation inst);
}
