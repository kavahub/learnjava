package io.github.kavahub.learnjava.lambda;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

import static io.github.kavahub.learnjava.lambda.LambdaExceptionWrappers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 
 * {@link LambdaExceptionWrappers} 应用示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
public class LambdaExceptionWrappersTest {
    private List<Integer> integers;

    @BeforeEach
    public void init() {
        integers = Arrays.asList(3, 9, 7, 0, 10, 20);
    }

    @Test
    public void whenNoExceptionFromLambdaWrapper_thenSuccess() {
        integers.forEach(lambdaWrapper(i -> log.debug("{}", 50 / i)));
    }

    @Test
    public void whenNoExceptionFromConsumerWrapper_thenSuccess() {
        integers.forEach(consumerWrapper(i -> log.debug("{}", 50 / i), ArithmeticException.class));
    }

    @Test
    public void whenExceptionFromThrowingConsumerWrapper_thenSuccess() {
        assertThrows(RuntimeException.class, () -> integers.forEach(throwingConsumerWrapper(i -> writeToFile(i))));
    }

    @Test
    public void whenNoExceptionFromHandlingConsumerWrapper_thenSuccess() {
        integers.forEach(handlingConsumerWrapper(i -> writeToFile(i), IOException.class));
    }

    private void writeToFile(Integer i) throws IOException {
        if (i == 0) {
            throw new IOException(); // mock IOException
        }
        log.debug("{}", i);
    }   
}
