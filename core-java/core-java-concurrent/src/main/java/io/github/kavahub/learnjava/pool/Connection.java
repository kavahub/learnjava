package io.github.kavahub.learnjava.pool;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Connection {
    private String name;

    public Connection(String name) {
        this.name = name;
        System.out.println("创建完成:" + name);
    }

    public synchronized void use() {
        System.out.println(name + " 使用连接中...");

        // 模拟长时间任务
        try {
            TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextLong(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(name + " 使用结束");
    }
}
