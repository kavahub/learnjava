package io.github.kavahub.learnjava;

import java.io.Serializable;

import lombok.Data;

@Data
public class Student implements Serializable {
    private String name;
    private int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
