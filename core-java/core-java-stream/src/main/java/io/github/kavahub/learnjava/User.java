package io.github.kavahub.learnjava;

import lombok.Data;

/**
 * 
 * （辅助类）
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
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
