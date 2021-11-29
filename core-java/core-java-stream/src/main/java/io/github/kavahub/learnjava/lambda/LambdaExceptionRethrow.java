package io.github.kavahub.learnjava.lambda;

import java.util.function.Consumer;
import java.util.function.Function;

import lombok.experimental.UtilityClass;

/**
 * 
 * 在Lambda表达式中抛出异常
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class LambdaExceptionRethrow {
    @FunctionalInterface
    public interface Consumer_WithExceptions<T, E extends Exception> {
        void accept(T t) throws E;
    }

    @FunctionalInterface
    public interface Function_WithExceptions<T, R, E extends Exception> {
        R apply(T t) throws E;
    }

    public <T, E extends Exception> Consumer<T> rethrowConsumer(Consumer_WithExceptions<T, E> consumer) throws E {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Exception exception) {
                throwActualException(exception);
            }
        };
    }

    public <T, R, E extends Exception> Function<T, R> rethrowFunction(Function_WithExceptions<T, R, E> function) throws E  {
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception exception) {
                throwActualException(exception);
                return null;
            }
        };
    }

    @SuppressWarnings("unchecked")
    private <E extends Exception> void throwActualException(Exception exception) throws E {
        throw (E) exception;
    }
}
