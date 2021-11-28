package io.github.kavahub.learnjava.threadlocal;

import java.util.UUID;

/**
 * 
 * 数据访问
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class UserRepository {
    String getUserNameForUserId(Integer userId) {
        return UUID.randomUUID().toString();
    }
}
