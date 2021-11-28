package io.github.kavahub.learnjava.threadlocal;

/**
 * 
 * 上下文
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class Context {
    private final String userName;

    Context(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Context{" +
          "userNameSecret='" + userName + '\'' +
          '}';
    }
}
