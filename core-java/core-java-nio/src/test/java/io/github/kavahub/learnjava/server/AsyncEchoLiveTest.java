package io.github.kavahub.learnjava.server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import io.github.kavahub.learnjava.server.tcp.AsyncCompletionHandlerServer;
import io.github.kavahub.learnjava.server.tcp.Client1;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AsyncEchoLiveTest {
    private Process server;
    private Client1 client;

    @BeforeAll
    public void setup() throws IOException, InterruptedException {
        server = AsyncCompletionHandlerServer.start();
        client = Client1.getInstance();
    }

    @Test
    public void givenServerClient_whenServerEchosMessage_thenCorrect() throws Exception {
        String resp1 = client.sendMessage("hello");
        String resp2 = client.sendMessage("world");
        assertEquals("hello", resp1);
        assertEquals("world", resp2);
    }

    @AfterAll
    public void teardown() throws IOException {
        client.stop();
        server.destroy();   
    }
}
