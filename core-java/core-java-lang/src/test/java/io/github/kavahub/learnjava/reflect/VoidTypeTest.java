package io.github.kavahub.learnjava.reflect;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.Callable;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

/**
 * {@link Void}类型示例 
 */
public class VoidTypeTest {
    @Test
    void givenVoidCallable_whenDiffer_thenReturnNull() throws Exception {
        Callable<Void> callable = new Callable<Void>() {
            @Override
            public Void call() {
                System.out.println("Hello!");
                return null;
            }
        };

        assertThat(Defer.defer(callable)).isNull();
    }

    @Test
    void givenVoidFunction_whenDiffer_thenReturnNull() {
        Function<String, Void> function = s -> {
            System.out.println("Hello " + s + "!");
            return null;
        };

        assertThat(Defer.defer(function, "World")).isNull();
    }

    public static class Defer {
        public static <V> V defer(Callable<V> callable) throws Exception {
            return callable.call();
        }

        public static <T, R> R defer(Function<T, R> function, T arg) {
            return function.apply(arg);
        }
    }

}
