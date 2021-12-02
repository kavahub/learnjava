package io.github.kavahub.learnjava.ws;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import io.github.kavahub.learnjava.SecurityContextHolder;
import jakarta.jws.HandlerChain;
import jakarta.jws.WebService;
import lombok.extern.slf4j.Slf4j;


/**
 * 学生服务实现
 * 
 * <p>
 * 此服务需要安全认知
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@Slf4j
@WebService(endpointInterface = "io.github.kavahub.learnjava.ws.StudentWS")
@HandlerChain(file="handler-chain.xml")
public class StudentWSImpl implements StudentWS{
    private List<Student> students = new ArrayList<Student>();

    public String hello(String name) {
        log.info(SecurityContextHolder.INSTANCE.currentInfo());

        Student student = new Student(name, LocalDate.of(2021, 12, 1));
        students.add(student);
        return "Hello " + name;
    }

    public String helloStudent(Student student) {
        log.info(SecurityContextHolder.INSTANCE.currentInfo());

        return "Hello " + student.getName();
    }

    public StudentList getStudents() {
        log.info(SecurityContextHolder.INSTANCE.currentInfo());
        
        return new StudentList(students);
    }

}
