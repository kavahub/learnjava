package io.github.kavahub.learnjava.lambda;

import java.util.function.Consumer;

import lombok.experimental.UtilityClass;

/**
 * Lambda表达式异常适配器
 * 
 */
@UtilityClass
public class LambdaExceptionWrappers {
    public Consumer<Integer> lambdaWrapper(Consumer<Integer> consumer) {
        return i -> {
            try {
                consumer.accept(i);
            } catch (ArithmeticException e) {
                System.err.println("Arithmetic Exception occured : " + e.getMessage());
            }
        };
    }

    <T, E extends Exception> Consumer<T> consumerWrapper(Consumer<T> consumer, Class<E> clazz) {
        return i -> {
            try {
                consumer.accept(i);
            } catch (Exception ex) {
                try {
                    E exCast = clazz.cast(ex);
                    System.err.println("Exception occured : " + exCast.getMessage());
                } catch (ClassCastException ccEx) {
                    throw ex;
                }
            }
        };
    }

    public <T> Consumer<T> throwingConsumerWrapper(ThrowingConsumer<T, Exception> throwingConsumer) {
        return i -> {
            try {
                throwingConsumer.accept(i);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    public <T, E extends Exception> Consumer<T> handlingConsumerWrapper(ThrowingConsumer<T, E> throwingConsumer, Class<E> exceptionClass) {
        return i -> {
            try {
                throwingConsumer.accept(i);
            } catch (Exception ex) {
                try {
                    E exCast = exceptionClass.cast(ex);
                    System.err.println("Exception occured : " + exCast.getMessage());
                } catch (ClassCastException ccEx) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }   
}
