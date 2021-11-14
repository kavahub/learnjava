package io.github.kavahub.learnjava.reduce;

import lombok.Getter;

@Getter
public class User {
    private final String name;
    private final int age;
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
    private final Rating rating = new Rating();  
}
