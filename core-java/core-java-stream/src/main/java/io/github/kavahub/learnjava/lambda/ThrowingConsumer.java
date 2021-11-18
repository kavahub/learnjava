package io.github.kavahub.learnjava.lambda;

/**
 * 抛出异常的消费者
 * 
 */
@FunctionalInterface
public interface ThrowingConsumer<T, E extends Exception> {
    void accept(T t) throws E;
}
