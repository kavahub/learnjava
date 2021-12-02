package io.github.kavahub.learnjava;

import static org.assertj.core.api.Assertions.assertThat;
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

/**
 * student 功能测试，内嵌命令行服务
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
public class StudentLiveTest {
    private static Process server;

    private static StudentWS studentProxy;

    @BeforeAll
    public static void setup() throws IOException, InterruptedException {
        // 命令行启动服务
        server = UseMain.start();
        // 等待启动完成
        TimeUnit.SECONDS.sleep(3);

        ServiceHelper.addStudentHandler();
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
        final String endpointResponse = studentProxy.hello("learnjava");
        assertEquals("Hello learnjava", endpointResponse);
    }

    @Test
    public void usingGetStudentsMethod_thenCorrect() {
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
        final Student student = new Student("John Doe");
        final String endpointResponse = studentProxy.helloStudent(student);
        assertEquals("Hello John Doe", endpointResponse);

    }

}
