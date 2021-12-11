package io.github.kavahub.learnjava;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import lombok.extern.slf4j.Slf4j;

/**
 * 使用premain方式提供随JVM启动的Agent，在MANIFEST.MF文件中，必须指定Premain-Class
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
@Slf4j
public class PremainAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        log.info("Agent called - {}", PremainAgent.class.getName());

        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader l, String className, Class<?> c,
                    ProtectionDomain d, byte[] b)
                    throws IllegalClassFormatException {
                log.info("transform class - {}", className);
                if (className.equals("java/lang/Integer")) {
                    IntegerClassWriter classWriter = new IntegerClassWriter(b);
                    return classWriter.addField();
                }
                return b;
            }
        });
    }
}
