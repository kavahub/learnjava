package io.github.kavahub.learnjava.threadlocal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SharedMapWithContext implements Runnable {
    public final static Map<Integer, Context> context = new ConcurrentHashMap<>();
    private final Integer userId;
    private UserRepository userRepository = new UserRepository();

    public SharedMapWithContext(Integer userId) {
        this.userId = userId;
    }

    @Override
    public void run() {
        String userName = userRepository.getUserNameForUserId(userId);
        context.put(userId, new Context(userName));
    }
    
}
