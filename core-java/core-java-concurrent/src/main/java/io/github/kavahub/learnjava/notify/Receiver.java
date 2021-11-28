package io.github.kavahub.learnjava.notify;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 接收器，处理接收数据
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
public class Receiver implements Runnable {
    @Getter
    private List<String> messages = new ArrayList<>();
    private Data load;
 
    public Receiver(Data load) {
        this.load = load;
    }
 
    public void run() {
        for(String receivedMessage = load.receive();
          !"End".equals(receivedMessage) ;
          receivedMessage = load.receive()) {
            log.info("Receive message: {}", receivedMessage);

            //Thread.sleep() to mimic heavy server-side processing
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(0, 500));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); 
                System.out.println("Thread Interrupted");
            }

            messages.add(receivedMessage);
        }
    }
    
}
