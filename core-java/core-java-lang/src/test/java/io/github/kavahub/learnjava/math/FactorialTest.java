package io.github.kavahub.learnjava.math;

import static org.assertj.core.api.Assertions.assertThat;
import static io.github.kavahub.learnjava.math.Factorial.*;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link Factorial} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class FactorialTest {
    @Test
    public void whenCalculatingFactorialUsingForLoop_thenCorrect() {
        int n = 5;

        assertThat(usingForLoop(n)).isEqualTo(120);
    }

    @Test
    public void whenCalculatingFactorialUsingStreams_thenCorrect() {
        int n = 5;

        assertThat(usingStreams(n)).isEqualTo(120);
    }

    @Test
    public void whenCalculatingFactorialUsingRecursion_thenCorrect() {
        int n = 5;

        assertThat(usingRecursion(n)).isEqualTo(120);
    }

    @Test
    public void whenCalculatingFactorialUsingMemoize_thenCorrect() {
        int n = 5;

        assertThat(usingMemoize(n)).isEqualTo(120);

        n = 6;

        assertThat(usingMemoize(n)).isEqualTo(720);
    }

    @Test
    public void whenCalculatingFactorialHavingLargeResult_thenCorrect() {
        int n = 22;

        assertThat(factorialHavingLargeResult(n)).isEqualTo(new BigInteger("1124000727777607680000"));
    }

    @Test
    public void whenCalculatingFactorialUsingApacheCommons_thenCorrect() {
        int n = 5;

        assertThat(usingApacheCommons(n)).isEqualTo(120);
    }

    @Test
    public void whenCalculatingFactorialUsingGuava_thenCorrect() {
        int n = 22;

        assertThat(usingGuava(n)).isEqualTo(new BigInteger("1124000727777607680000"));
    }   
}
