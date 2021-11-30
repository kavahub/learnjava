package io.github.kavahub.learnjava;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * 学生集合
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@XmlType(name = "StudentMap")
public class StudentMap {
    private List<StudentEntry> entries = new ArrayList<StudentEntry>();

    @XmlElement(nillable = false, name = "entry")
    public List<StudentEntry> getEntries() {
        return entries;
    }

    @XmlType(name = "StudentEntry")
    public static class StudentEntry {
        private Integer id;
        private Student student;

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getId() {
            return id;
        }

        public void setStudent(Student student) {
            this.student = student;
        }

        public Student getStudent() {
            return student;
        }
    }
}
