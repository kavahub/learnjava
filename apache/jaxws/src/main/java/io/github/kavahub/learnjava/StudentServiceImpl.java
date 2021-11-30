package io.github.kavahub.learnjava;

import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.jws.WebService;


/**
 * 学生服务实现
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@WebService(endpointInterface = "io.github.kavahub.learnjava.StudentService")
public class StudentServiceImpl implements StudentService{
    private Map<Integer, Student> students = new LinkedHashMap<Integer, Student>();

    public String hello(String name) {
        return "Hello " + name;
    }

    public String helloStudent(Student student) {
        students.put(students.size() + 1, student);
        return "Hello " + student.getName();
    }

    public Map<Integer, Student> getStudents() {
        return students;
    }
}
