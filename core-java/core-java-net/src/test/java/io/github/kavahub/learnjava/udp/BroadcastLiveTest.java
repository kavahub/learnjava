package io.github.kavahub.learnjava.udp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

/**
 * 
 * {@link BroadcastingEchoServer} 应用示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class BroadcastLiveTest {
    private static BroadcastingClient client;

    @Test
    public void whenBroadcasting_thenDiscoverExpectedServers() throws Exception {
        int expectedServers = 4;
        initializeForExpectedServers(expectedServers);

        int serversDiscovered = client.discoverServers("hello server");
        assertEquals(expectedServers, serversDiscovered);
    }

    private void initializeForExpectedServers(int expectedServers) throws Exception {
        for (int i = 0; i < expectedServers; i++) {
            new BroadcastingEchoServer().start();
        }

        client = new BroadcastingClient(expectedServers);
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
