package io.github.kavahub.learnjava.tcp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class AsyncEchoLiveTest {
    private static Process server;
    private static Client1 client;

    @BeforeAll
    public static void setup() throws IOException, InterruptedException {
        server = AsyncCompletionHandlerServer.start();
        client = Client1.getInstance();
    }

    @AfterAll
    public static void teardown() throws IOException {
        client.stop();
        server.destroy();   
    }
    
    @Test
    public void givenServerClient_whenServerEchosMessage_thenCorrect() throws Exception {
        String resp1 = client.sendMessage("hello");
        String resp2 = client.sendMessage("world");
        assertEquals("hello", resp1);
        assertEquals("world", resp2);
    }


}
