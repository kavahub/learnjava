package io.github.kavahub.learnjava.udp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * 
 * {@link EchoServer} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class EchoLiveTest {
    private static EchoClient client;

    @BeforeAll
    public static void setup() throws IOException {
        new EchoServer().start();
        client = new EchoClient();
    }

    @Test
    public void whenCanSendAndReceivePacket_thenCorrect1() {
        String echo = client.sendEcho("hello server");
        assertEquals("hello server", echo);
        echo = client.sendEcho("server is working");
        assertFalse(echo.equals("hello server"));
    }

    @Test
    public void whenCanSendChinesePacket_thenCorrect1() {
        final String message = "你好，测试消息";
        String echo = client.sendEcho(message);
        assertEquals(message, echo);
    }

    @AfterAll
    public static void tearDown() {
        stopEchoServer();
        client.close();
    }

    private static void stopEchoServer() {
        client.sendEcho("end");
    }   
}
