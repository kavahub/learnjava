package io.github.kavahub.learnjava;

import java.lang.instrument.Instrumentation;

import lombok.extern.slf4j.Slf4j;

/**
 * 代理入口
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@Slf4j
public class PremainAgent {
    public static void premain(String args, Instrumentation inst){
        log.info(">>> Agent called -> {}", PremainAgent.class.getName());
        
        // 注册转换器, 可以切换两中实现: Javassist 和 asm
        //inst.addTransformer(new ClassFileTransformerWithJavassist());
        inst.addTransformer(new ClassFileTransformerWithJavassistStopWatch());
        //inst.addTransformer(new ClassFileTransformerWithASM());
    }
}
