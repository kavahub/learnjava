package io.github.kavahub.learnjava.plugins.jvm;

import net.bytebuddy.asm.Advice;

/**
 * TODO
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public class JVMAdvice {
    @Advice.OnMethodExit()
    public static void exit() {
        final String memoryInfo = JVMInfo.getMemoryInfo();
        final String gcInfo = JVMInfo.getGCInfo();
        System.out.println("--------------------------------- JVM追踪(MQ) ---------------------------------");
        System.out.println(memoryInfo);
        System.out.println(gcInfo);
        System.out.println("-------------------------------------------------------------------------------");
    }
}
