package io.github.kavahub.learnjava.elapse;

import java.lang.instrument.Instrumentation;

/**
 * TODO
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public class PrmainAgent {
    public static void premain(String args, Instrumentation inst){
        System.out.println(">>> PrmainAgent.premain called ");
        // 注册转换器
        inst.addTransformer(new TransformerWithJavassist());
    }
}
