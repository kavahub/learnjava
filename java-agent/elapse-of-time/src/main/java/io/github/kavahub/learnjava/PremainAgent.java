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
        log.info("Agent called - {}", PremainAgent.class.getName());
        
        Transformer transformer = TransformerProvider.INSTANCE.get();
        if (transformer != null) {
            transformer.transform(args, inst);
            log.info("Transformer registered successfully -> {}", transformer.getClass().getName());
        } else {
            log.warn("Agent failure, because of transformer is not configured correctly");
        }
        
    }
}
