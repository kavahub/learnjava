package io.github.kavahub.learnjava.notify;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

/**
 * 
 * Notify 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 * 
 * @see Data
 * @see Receiver
 * @see Sender
 */
public class NotifyTest {
    @Test
    public void givenSenderAndReceiver_whenSendingPackets_thenNetworkSynchronized() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Data data = new Data();
        Sender sender = new Sender(data);
        Receiver receiver = new Receiver(data);
        
        pool.submit(receiver);
        pool.submit(sender);

        pool.shutdown();
        pool.awaitTermination(60, TimeUnit.SECONDS);

        assertThat(receiver.getMessages()).containsExactly("First packet","Second packet","Third packet","Fourth packet");
    }

}
