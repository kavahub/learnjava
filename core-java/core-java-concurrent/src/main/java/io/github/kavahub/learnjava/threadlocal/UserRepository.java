package io.github.kavahub.learnjava.threadlocal;

import java.util.UUID;

public class UserRepository {
    String getUserNameForUserId(Integer userId) {
        return UUID.randomUUID().toString();
    }
}
