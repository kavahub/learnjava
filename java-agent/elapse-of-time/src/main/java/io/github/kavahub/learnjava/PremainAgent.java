package io.github.kavahub.learnjava;

import java.lang.instrument.ClassFileTransformer;
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
        
        ClassFileTransformer cft = ClassFileTransformerProvider.INSTANCE.get();
        if (cft != null) {
            // 注册转换器
            inst.addTransformer(cft);
            log.info("Transformer registered successfully -> {}", cft.getClass().getName());
        } else {
            log.warn("Agent failure, because of transformer is not configured correctly");
        }
        
    }
}
