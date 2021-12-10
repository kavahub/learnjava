package io.github.kavahub.learnjava.integer;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * 使用premain方式提供随JVM启动的Agent，在MANIFEST.MF文件中，必须指定Premain-Class
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public class PrmainAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println(" >>> IntegerPremain.premain called ");
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader l, String className, Class<?> c,
                    ProtectionDomain d, byte[] b)
                    throws IllegalClassFormatException {
                System.out.println(">>> transform class: " + className);
                if (className.equals("java/lang/Integer")) {
                    CustomClassWriter cr = new CustomClassWriter(b);
                    return cr.addField();
                }
                return b;
            }
        });
    }
}
