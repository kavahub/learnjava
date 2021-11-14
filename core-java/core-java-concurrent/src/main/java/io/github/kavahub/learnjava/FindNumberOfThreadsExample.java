package io.github.kavahub.learnjava;

import java.lang.management.ManagementFactory;

public class FindNumberOfThreadsExample {
    public static void main(String[] args) {
        System.out.println("Number of threads " + Thread.activeCount());
        System.out.println("Current Thread Group - "
            + Thread.currentThread().getThreadGroup().getName());
        System.out.println("Total Number of threads "
            + ManagementFactory.getThreadMXBean().getThreadCount());
    }
}
