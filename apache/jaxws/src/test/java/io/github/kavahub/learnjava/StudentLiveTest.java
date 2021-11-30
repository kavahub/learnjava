package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.xml.namespace.QName;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.xml.ws.Service;

/**
 * TODO
 * 
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
public class StudentLiveTest {

    private static QName SERVICE_NAME = new QName("http://learnjava.kavahub.github.io/", "StudentServiceImplService");
    private static QName PORT_NAME = new QName("http://learnjava.kavahub.github.io/", "StudentServiceImplPort");
    private static Process server;

    private static Service service;
    private StudentService studentProxy;

    @BeforeAll
    public static void setup() throws IOException, InterruptedException {
        server = Server.start();
        // 等待启动完成
        TimeUnit.SECONDS.sleep(1);

        service = Service.create(new URL("http://localhost:9080/student?wsdl"), SERVICE_NAME);
    }

    @AfterAll
    public static void teardown() throws IOException {
        server.destroy();
    }

    @BeforeEach
    public void reinstantiateStudentInstances() {
        studentProxy = service.getPort(PORT_NAME, StudentService.class);
    }

    @Test
    public void whenUsingHelloMethod_thenCorrect() {
        final String endpointResponse = studentProxy.hello("learnjava");
        assertEquals("Hello learnjava", endpointResponse);
    }

    @Test
    public void usingGetStudentsMethod_thenCorrect() {
        final Student student1 = new StudentImpl("Adam");
        studentProxy.helloStudent(student1);

        final Student student2 = new StudentImpl("Eve");
        studentProxy.helloStudent(student2);

        final Map<Integer, Student> students = studentProxy.getStudents();
        assertTrue(students.size() >= 2);

        String names = students.values().stream().map(Student::getName).collect(Collectors.joining(","));
        assertTrue(names.indexOf("Adam") >= 0);
        assertTrue(names.indexOf("Eve") >= 0);
    }

    @Test
    public void whenUsingHelloStudentMethod_thenCorrect() {
        final Student student = new StudentImpl("John Doe");
        final String endpointResponse = studentProxy.helloStudent(student);
        assertEquals("Hello John Doe", endpointResponse);
    }

}
