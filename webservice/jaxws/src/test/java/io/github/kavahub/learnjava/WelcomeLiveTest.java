package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.github.kavahub.learnjava.publish.UseMain;
import io.github.kavahub.learnjava.ws.WelcomeWS;

/**
 * welcome 功能测试
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
public class WelcomeLiveTest {
    private static Process server;

    private static WelcomeWS welcomeProxy;

    @BeforeAll
    public static void setup() throws IOException, InterruptedException {
        server = UseMain.start();
        // 等待启动完成
        TimeUnit.SECONDS.sleep(3);

        // ServiceHelper.addWelcomeHandler();;
        welcomeProxy = ServiceHelper.getWelcomeWS();
    }

    @AfterAll
    public static void teardown() throws IOException, InterruptedException {
        server.destroy();

        // 等待关闭
        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    public void whenSay_thenCorrect() {
        final String endpointResponse = welcomeProxy.say("learnjava");
        assertEquals("learnjava Welcome", endpointResponse);
    }
}
