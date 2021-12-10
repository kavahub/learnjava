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
        
        // 注册转换器, 可以切换两中实现: Javassist 和 asm
        // inst.addTransformer(new TransformerWithJavassist());
        inst.addTransformer(new TransformerWithASM());
    }
}
