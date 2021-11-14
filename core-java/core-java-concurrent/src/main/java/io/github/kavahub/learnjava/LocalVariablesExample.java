package io.github.kavahub.learnjava;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LocalVariablesExample {
    private int field;

    private Runnable task1 = () -> {
        int localfield = new SecureRandom().nextInt();
        int local = new SecureRandom().nextInt();

        this.field = localfield;
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("taks1 {} - {}", field, local);
    };

    private Runnable task2 = () -> {
        int localfield = new SecureRandom().nextInt();
        int local = new SecureRandom().nextInt();

        this.field = localfield;
        log.info("taks2 {} - {}", field, local);
    };


    public static void main(String... args) {
        LocalVariablesExample target = new LocalVariablesExample();
        new Thread(target.task1).start();
        new Thread(target.task1).start();
        new Thread(target.task2).start();
        new Thread(target.task2).start();
    }
}
