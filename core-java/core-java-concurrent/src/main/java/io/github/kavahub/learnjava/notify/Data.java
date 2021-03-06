package io.github.kavahub.learnjava.notify;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 控制器，控制发送接收的状态
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
public class Data {
    private String packet;
    
    // True if receiver should wait
    // False if sender should wait
    private boolean transfer = true;
 
    public synchronized String receive() {
        while (transfer) {
            try {
                log.info("Receive waitting...");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); 
                log.error("Thread Interrupted");
            }
        }
        transfer = true;

        notifyAll();
        return packet;
    }
 
    public synchronized void send(String packet) {
        while (!transfer) {
            try { 
                log.info("Send waitting...");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); 
                log.error("Thread Interrupted");
            }
        }
        transfer = false;
        
        this.packet = packet;
        notifyAll();
    }
}
