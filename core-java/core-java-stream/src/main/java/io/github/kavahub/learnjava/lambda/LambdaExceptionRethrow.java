package io.github.kavahub.learnjava.lambda;

import java.util.function.Consumer;
import java.util.function.Function;

import lombok.experimental.UtilityClass;

/**
 * 在Lambda表达式中抛出异常
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

    /**
     * .forEach(rethrowConsumer(name -> System.out.println(Class.forName(name))));
     */
    public <T, E extends Exception> Consumer<T> rethrowConsumer(Consumer_WithExceptions<T, E> consumer) throws E {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Exception exception) {
                throwActualException(exception);
            }
        };
    }

    /**
     * .map(rethrowFunction(name -> Class.forName(name))) or .map(rethrowFunction(Class::forName))
     */
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