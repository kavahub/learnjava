package io.github.kavahub.learnjava;

import java.util.Map;

import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 学生服务
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@WebService
public interface StudentService {
    public String hello(String name);

    public String helloStudent(Student student);

    @XmlJavaTypeAdapter(StudentMapAdapter.class)
    public Map<Integer, Student> getStudents();
}
