package io.github.kavahub.learnjava.udp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

/**
 * 
 * {@link MulticastEchoServer} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class MulticastLiveTest {
    private static MulticastingClient client;

    @Test
    public void whenBroadcasting_thenDiscoverExpectedServers() throws Exception {
        int expectedServers = 4;
        initializeForExpectedServers(expectedServers);

        int serversDiscovered = client.discoverServers("hello server");
        assertEquals(expectedServers, serversDiscovered);
    }

    private void initializeForExpectedServers(int expectedServers) throws Exception {
        for (int i = 0; i < expectedServers; i++) {
            new MulticastEchoServer().start();
        }

        client = new MulticastingClient(expectedServers);
    }

    @AfterAll
    public static void tearDown() throws IOException {
        stopEchoServer();
        client.close();
    }

    private static void stopEchoServer() throws IOException {
        client.discoverServers("end");
    }    
}
