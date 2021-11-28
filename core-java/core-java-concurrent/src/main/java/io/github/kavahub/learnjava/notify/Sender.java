package io.github.kavahub.learnjava.notify;

import java.util.concurrent.ThreadLocalRandom;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 发送器，处理发送数据
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
public class Sender implements Runnable {
    private Data data;
 
    public Sender(Data data) {
        this.data = data;
    }
 
    public void run() {
        String packets[] = {
          "First packet",
          "Second packet",
          "Third packet",
          "Fourth packet",
          "End"
        };
 
        for (String packet : packets) {
            log.info("Send message:{}", packet);
            
            //Thread.sleep() to mimic heavy server-side processing
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(0, 500));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); 
                System.out.println("Thread Interrupted");
            }
            data.send(packet);
        }
    }
    
}
