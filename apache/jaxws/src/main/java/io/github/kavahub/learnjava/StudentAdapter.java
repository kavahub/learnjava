package io.github.kavahub.learnjava;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * 学生适配器
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public class StudentAdapter extends XmlAdapter<StudentImpl, Student> {
    public StudentImpl marshal(Student student) throws Exception {
        if (student instanceof StudentImpl) {
            return (StudentImpl) student;
        }
        return new StudentImpl(student.getName());
    }

    public Student unmarshal(StudentImpl student) throws Exception {
        return student;
    }
    
}
