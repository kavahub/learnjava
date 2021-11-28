package io.github.kavahub.learnjava;

import java.lang.management.ManagementFactory;

/**
 * 
 * JVM线程信息
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class JVMThreadInfoExample {
    public static void main(String[] args) {
        System.out.println("Number of threads " + Thread.activeCount());
        System.out.println("Current Thread Group - "
            + Thread.currentThread().getThreadGroup().getName());
        System.out.println("Total Number of threads "
            + ManagementFactory.getThreadMXBean().getThreadCount());
    }
}
