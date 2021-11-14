package io.github.kavahub.learnjava;

import static io.github.kavahub.learnjava.LambdaExceptionHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class LambdaExceptionHelperTest {
    @Test
    public void testConsumer() throws MyTestException {
        assertThrows(MyTestException.class,
                () -> Stream.of((String) null).forEach(rethrowConsumer(s -> checkValue(s))));
    }

    private void checkValue(String value) throws MyTestException {
        if (value == null) {
            throw new MyTestException();
        }
    }

    private class MyTestException extends Exception {
    }

    @Test
    public void testConsumerRaisingExceptionInTheMiddle() {
        MyLongAccumulator accumulator = new MyLongAccumulator();
        try {
            Stream.of(2L, 3L, 4L, null, 5L).forEach(rethrowConsumer(s -> accumulator.add(s)));
            fail();
        } catch (MyTestException e) {
            assertEquals(9L, accumulator.acc);
        }
    }

    private class MyLongAccumulator {
        private long acc = 0;

        public void add(Long value) throws MyTestException {
            if (value == null) {
                throw new MyTestException();
            }
            acc += value;
        }
    }

    @Test
    public void testFunction() throws MyTestException {
        List<Integer> sizes = Stream.of("ciao", "hello").<Integer>map(rethrowFunction(s -> transform(s)))
                .collect(Collectors.toList());
        assertEquals(2, sizes.size());
        assertEquals(4, sizes.get(0).intValue());
        assertEquals(5, sizes.get(1).intValue());
    }

    private Integer transform(String value) throws MyTestException {
        if (value == null) {
            throw new MyTestException();
        }
        return value.length();
    }

    @Test
    public void testFunctionRaisingException() throws MyTestException {
        assertThrows(MyTestException.class, () -> Stream.of("ciao", null, "hello")
                .<Integer>map(rethrowFunction(s -> transform(s))).collect(Collectors.toList()));
    }
}
