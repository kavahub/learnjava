package io.github.kavahub.learnjava.ws;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;

/**
 * 学生集合
 * 
 * <p>
 * JAX-WS不支持直接使用集合
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@Data
@XmlType
public class StudentList {
    private List<Student> students;

    public StudentList() {
        students = new ArrayList<Student>();
    }

    public StudentList(List<Student> students) {
        this.students = students;
    }
}
