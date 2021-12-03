package io.github.kavahub.learnjava;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.github.kavahub.learnjava.publish.UseMain;
import io.github.kavahub.learnjava.ws.Student;
import io.github.kavahub.learnjava.ws.StudentWS;
import io.github.kavahub.learnjava.ws.WelcomeWS;
import jakarta.xml.ws.soap.SOAPFaultException;

/**
 * WebService 集成测试
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
public class WebServiceLiveTest {
    private static Process server;

    @BeforeAll
    public static void setup() throws IOException, InterruptedException {
        // 命令行启动服务
        server = UseMain.start();
        // 等待启动完成
        TimeUnit.SECONDS.sleep(3);
    }

    @AfterAll
    public static void teardown() throws IOException, InterruptedException {
        server.destroy();

        // 等待关闭
        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    public void whenUsingHelloMethod_thenCorrect() {
        StudentWS studentProxy = ServiceHelper.getStudentWSAuth();
        final String endpointResponse = studentProxy.hello("learnjava");
        assertEquals("Hello learnjava", endpointResponse);
    }

    @Test
    public void usingGetStudentsMethod_thenCorrect() {
        StudentWS studentProxy = ServiceHelper.getStudentWSAuth();

        studentProxy.hello("Adam");
        studentProxy.hello("Eve");

        final List<Student> students = studentProxy.getStudents().getStudents();
        assertTrue(students.size() >= 2);
        assertThat(students.get(0).getBirthday().toString()).isEqualTo("2021-12-01");

        String names = students.stream().map(Student::getName).collect(Collectors.joining(","));
        assertTrue(names.indexOf("Adam") >= 0);
        assertTrue(names.indexOf("Eve") >= 0);

        System.out.print(students);

    }

    @Test
    public void whenUsingHelloStudentMethod_thenCorrect() {
        StudentWS studentProxy = ServiceHelper.getStudentWSAuth();

        final Student student = new Student("John Doe");
        final String endpointResponse = studentProxy.helloStudent(student);
        assertEquals("Hello John Doe", endpointResponse);

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
