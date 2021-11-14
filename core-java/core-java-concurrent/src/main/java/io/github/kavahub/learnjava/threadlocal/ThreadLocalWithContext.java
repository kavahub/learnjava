package io.github.kavahub.learnjava.threadlocal;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalWithContext implements Runnable {   
    private static final ThreadLocal<Context> context = new ThreadLocal<>();
    private final Integer userId;
    private UserRepository userRepository = new UserRepository();

    public ThreadLocalWithContext(Integer userId) {
        this.userId = userId;
    }


    @Override
    public void run() {
        String userName = userRepository.getUserNameForUserId(userId);
        context.set(new Context(userName));
        log.debug("thread context for given userId: " + userId + " is: " + context.get());
    }
    
}
