package io.github.kavahub.learnjava.socket;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 * {@link EchoClient} 应用示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class EchoLiveTest {
    private static int port;

    @BeforeAll
    public static void start() throws InterruptedException, IOException {
        
        // Take an available port
        ServerSocket s = new ServerSocket(0);
        port = s.getLocalPort();
        s.close();

        Executors.newSingleThreadExecutor()
            .submit(() -> new EchoServer().start(port));
        Thread.sleep(500);
    }

    private EchoClient client = new EchoClient();

    @BeforeEach
    public void init() {
        client.startConnection("127.0.0.1", port);
    }

    @AfterEach
    public void tearDown() {
        client.stopConnection();
    }

    //

    @Test
    public void givenClient_whenServerEchosMessage_thenCorrect() {
        String resp0 = client.sendMessage("中文");
        String resp1 = client.sendMessage("hello");
        String resp2 = client.sendMessage("world");
        String resp3 = client.sendMessage("!");
        String resp4 = client.sendMessage(".");
        assertEquals("中文", resp0);
        assertEquals("hello", resp1);
        assertEquals("world", resp2);
        assertEquals("!", resp3);
        assertEquals("good bye", resp4);
    }
   
}
