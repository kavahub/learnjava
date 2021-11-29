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
 * {@link GreetServer} 应用实例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class GreetServerLiveTest {
    private GreetClient client;

    private static int port;

    @BeforeAll
    public static void start() throws InterruptedException, IOException {
        
        // Take an available port
        ServerSocket s = new ServerSocket(0);
        port = s.getLocalPort();
        s.close();
        
        Executors.newSingleThreadExecutor()
            .submit(() -> new GreetServer().start(port));
        Thread.sleep(500);
    }

    @BeforeEach
    public void init() {
        client = new GreetClient();
        client.startConnection("127.0.0.1", port);

    }

    @Test
    public void givenGreetingClient_whenServerRespondsWhenStarted_thenCorrect() {
        String response = client.sendMessage("hello server");
        String response1 = client.sendMessage("hello");
        assertEquals("hello client", response);
        assertEquals("unrecognised greeting", response1);

        client.sendMessage(".");
    }

    @AfterEach
    public void finish() {
        client.stopConnection();
    }    
}
