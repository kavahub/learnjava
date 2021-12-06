package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 需要手工运行服务端，有多种方式运行，请参考 README.md 文档
 * 
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public class StudentIntegrationTest {
    private ApplicationContext context = new AnnotationConfigApplicationContext(ClientConfiguration.class);
    private StudentWS studentProxy = (StudentWS) context.getBean("client");

    @Test
    public void whenUsingHelloMethod_thenCorrect() {
        String response = studentProxy.hello("John Doe");
        assertEquals("Hello John Doe!", response);
    }

    @Test
    public void whenUsingRegisterMethod_thenCorrect() {
        Student student1 = new Student("Adam");
        Student student2 = new Student("Eve");
        String student1Response = studentProxy.register(student1);
        String student2Response = studentProxy.register(student2);

        assertEquals("Adam is registered student number 1", student1Response);
        assertEquals("Eve is registered student number 2", student2Response);
    }
}
