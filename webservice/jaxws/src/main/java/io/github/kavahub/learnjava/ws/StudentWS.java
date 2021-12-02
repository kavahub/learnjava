package io.github.kavahub.learnjava.ws;

import jakarta.jws.WebService;


/**
 * 学生服务
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@WebService
public interface StudentWS {
    public String hello(String name);

    public String helloStudent(Student student);

    public StudentList getStudents();
}
