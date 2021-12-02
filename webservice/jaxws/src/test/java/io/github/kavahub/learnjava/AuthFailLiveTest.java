package io.github.kavahub.learnjava;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.github.kavahub.learnjava.publish.UseMain;
import io.github.kavahub.learnjava.ws.StudentWS;
import jakarta.xml.ws.soap.SOAPFaultException;

/**
 * 登录失败测试，内嵌命令行服务
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
public class AuthFailLiveTest {
    private static Process server;

    private static StudentWS studentProxy;

    @BeforeAll
    public static void setup() throws IOException, InterruptedException {
        // 命令行启动服务
        server = UseMain.start();
        // 等待启动完成
        TimeUnit.SECONDS.sleep(3);

        // 没有添加安全认证头
        //ServiceHelper.addStudentHandler();
        studentProxy = ServiceHelper.getStudentWS();
    }

    @AfterAll
    public static void teardown() throws IOException, InterruptedException {
        server.destroy();

        // 等待关闭
        TimeUnit.SECONDS.sleep(1);
    }
    
    @Test
    public void whenUsingHelloMethod_thenCorrect() {
        assertThatThrownBy(() -> studentProxy.hello("learnjava"))
                .isInstanceOf(SOAPFaultException.class).hasMessageContaining("授权校验失败：用户名或密码错误");
    }

}
