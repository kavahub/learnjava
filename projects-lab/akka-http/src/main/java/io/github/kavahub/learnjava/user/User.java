package io.github.kavahub.learnjava.user;

import lombok.Data;

/**
 * 用户
 *  
 * @author PinWei Wan
 * @since 1.0.2
 */
@Data
public class User {
    private final Long id;

    private final String name;

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    
}
