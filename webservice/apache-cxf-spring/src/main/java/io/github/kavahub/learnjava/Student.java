package io.github.kavahub.learnjava;

import lombok.Data;

/**
 * TODO
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@Data
public class Student {
    private String name;

    Student() {
    }

    public Student(String name) {
        this.name = name;
    }
}
