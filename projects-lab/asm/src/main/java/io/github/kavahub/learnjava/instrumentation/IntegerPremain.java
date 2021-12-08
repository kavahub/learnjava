package io.github.kavahub.learnjava.instrumentation;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import io.github.kavahub.learnjava.CustomClassWriter;

/**
 * TODO
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public class IntegerPremain {
    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformer() {

            @Override
            public byte[] transform(ClassLoader l, String name, Class<?> c,
                    ProtectionDomain d, byte[] b)
                    throws IllegalClassFormatException {

                if (name.equals("java/lang/Integer")) {
                    CustomClassWriter cr = new CustomClassWriter(b);
                    return cr.addField();
                }
                return b;
            }
        });
    }
}
