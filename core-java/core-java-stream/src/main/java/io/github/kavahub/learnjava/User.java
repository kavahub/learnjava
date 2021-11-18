package io.github.kavahub.learnjava;

import lombok.Data;

@Data
public class User {
    private final String name;
    private final int age;
    private Rating rating = new Rating();

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
