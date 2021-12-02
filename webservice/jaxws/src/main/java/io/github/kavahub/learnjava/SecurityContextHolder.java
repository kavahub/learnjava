package io.github.kavahub.learnjava;

import lombok.Data;

/**
 * 安全上下文
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public class SecurityContextHolder {
    public final static SecurityContextHolder INSTANCE = new SecurityContextHolder();

    private final static ThreadLocal<Context> contextHolder = new ThreadLocal<Context>();

    public void setUsername(String username) {
        contextHolder.set(new Context(username));
    }

    public String getUsername() {
        Context context = contextHolder.get();
        if (context == null) {
            return null;
        }

        return context.getUsername();
    }

    public void remove() {
        contextHolder.remove();
    }

    public String currentInfo() {
        return "登录用户: " +SecurityContextHolder.INSTANCE.getUsername();
    }

    @Data
    private static class Context {
        private String username;

        public Context(String username) {
            this.username = username;
        }


    }
}
