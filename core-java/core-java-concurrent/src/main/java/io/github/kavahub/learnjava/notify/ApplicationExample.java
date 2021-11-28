package io.github.kavahub.learnjava.notify;

/**
 * 
 * 程序入口
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ApplicationExample {
    public static void main(String[] args) {
        Data data = new Data();
        Thread sender = new Thread(new Sender(data));
        Thread receiver = new Thread(new Receiver(data));
        
        sender.start();
        receiver.start();
    }
}
