package io.github.kavahub.learnjava.notify;

import java.util.concurrent.ThreadLocalRandom;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Receiver implements Runnable {
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
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); 
                System.out.println("Thread Interrupted");
            }
        }
    }
    
}