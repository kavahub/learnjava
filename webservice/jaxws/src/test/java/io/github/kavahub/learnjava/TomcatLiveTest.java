package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.catalina.LifecycleException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.github.kavahub.learnjava.ws.StudentWS;

/**
 * 内嵌tomcat测试
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
public class TomcatLiveTest {
    private static StudentWS studentProxy;

    @BeforeAll
    public static void setup() throws IOException, InterruptedException, LifecycleException {
        TomcatServer.INSTANCE.start();

        // 等待启动完成
        TimeUnit.SECONDS.sleep(1);

        ServiceHelper.addStudentHandler();
        studentProxy = ServiceHelper.getStudentWS();
    }

    @AfterAll
    public static void teardown() throws IOException, InterruptedException, LifecycleException {
        TomcatServer.INSTANCE.stop();

        // 等待关闭
        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    public void whenUsingHelloMethod_thenCorrect() {
        final String endpointResponse = studentProxy.hello("learnjava");
        assertEquals("Hello learnjava", endpointResponse);
    }

}
