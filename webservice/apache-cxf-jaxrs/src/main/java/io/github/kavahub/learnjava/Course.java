package io.github.kavahub.learnjava;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

/**
 * 课程
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@Data
@XmlRootElement(name = "Course")
public class Course{
    private int id;
    private String name;
    private List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
    }

    public Response getStudent(int studentId) {
        Student student = findById(studentId);
        if (student == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(student).build();
    }

    public Response createStudent(Student student) {
        if (findById(student.getId()) != null) {
            return Response.status(Response.Status.CONFLICT).build();
        }
 
        students.add(student);
        return Response.ok(student).build();
    }

    public Response deleteStudent(int studentId) {
        Student student = findById(studentId);
        if (student == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        students.remove(student);
        return Response.ok().build();
    }

    private Student findById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    @Override
    public int hashCode() {
        return id + name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Course) && (id == ((Course) obj).getId()) && (name.equals(((Course) obj).getName()));
    }
}
