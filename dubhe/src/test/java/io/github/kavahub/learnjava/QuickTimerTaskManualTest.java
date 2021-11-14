package io.github.kavahub.learnjava;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class QuickTimerTaskManualTest {
    @Test
    public void whenStaticCreate_thenRun() throws InterruptedException {
        QuickTimerTask.scheduleAtFixedRate(() -> {
            System.out.println("QuickTimerTask run");
        }, 100, 500);

        TimeUnit.SECONDS.sleep(1);
    } 

    @Test
    public void whenInheritCreate_thenRun() throws InterruptedException {
        new QuickTimerTask() {
            @Override
            public void run() {
                System.out.println("QuickTimerTask run");
                
            }

            @Override
            protected long getPeriod() {
                return 100;
            }
            
        };

        TimeUnit.SECONDS.sleep(1);
    } 
}
