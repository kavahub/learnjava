package io.github.kavahub.learnjava.ws;

import java.time.LocalDate;

import io.github.kavahub.learnjava.LocalDateAdapter;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.ToString;

/**
 * 学生实体
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@ToString
@XmlType
public class Student {
    private String name;

    private LocalDate birthday;
    
    public Student() {
    }

    public Student(String name) {
        this.name = name;
    }

    public Student(String name, LocalDate birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    
}
