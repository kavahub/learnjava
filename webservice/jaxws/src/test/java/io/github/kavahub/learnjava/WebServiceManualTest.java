package io.github.kavahub.learnjava;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.github.kavahub.learnjava.ws.StudentWS;
import io.github.kavahub.learnjava.ws.WelcomeWS;
import jakarta.xml.ws.soap.SOAPFaultException;

/**
 * 在执行测试之前，请先启动服务
 * 
 * <p>
 * 本项目依赖Servlet API 5.0的版本， apache-tomcat-10.0.13 这个版本是支持的。
 * 
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
public class WebServiceManualTest {
    
    @Test
    public void whenUsingHelloMethod_thenCorrect() {
        StudentWS studentProxy = ServiceHelper.getStudentWSAuth();
        final String endpointResponse = studentProxy.hello("learnjava");
        assertEquals("Hello learnjava", endpointResponse);
    }


    @Test
    public void whenSay_thenCorrect() {
        WelcomeWS welcomeProxy = ServiceHelper.getWelcomeWS();

        final String endpointResponse = welcomeProxy.say("learnjava");
        assertEquals("learnjava Welcome", endpointResponse);
    }

    @Test
    public void whenNoAuth_thenCorrect() {
        StudentWS studentProxy = ServiceHelper.getStudentWSNoAuth();

        assertThatThrownBy(() -> studentProxy.hello("learnjava"))
                .isInstanceOf(SOAPFaultException.class).hasMessageContaining("授权校验失败：用户名或密码错误");
    }

}
