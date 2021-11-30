package io.github.kavahub.learnjava;

import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;

/**
 * 学生实现
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@XmlType(name = "Student")
@Data
public class StudentImpl implements Student {
    private String name;

    StudentImpl() {
    }

    public StudentImpl(String name) {
        this.name = name;
    }
    
}
