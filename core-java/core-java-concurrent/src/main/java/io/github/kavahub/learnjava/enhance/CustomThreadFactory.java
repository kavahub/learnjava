package io.github.kavahub.learnjava.enhance;

import java.util.concurrent.ThreadFactory;

/**
 * 自定义 {@link ThreadFactory}
 */
public class CustomThreadFactory implements ThreadFactory {
    private int threadId;
    private final String name;

    public CustomThreadFactory(String name) {
        threadId = 1;
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, name + "-Thread_" + threadId);
        System.out.println("created new thread with id : " + threadId + " and name : " + t.getName());
        threadId++;
        return t;
    }

    public static void main(String[] args) {
		CustomThreadFactory factory = new CustomThreadFactory("CustomThreadFactory");
		for (int i = 0; i < 10; i++) {
			Thread t = factory.newThread(() -> {
                System.out.println(Thread.currentThread().getName() + " started");
            });
			t.start();
		}
    }
}
