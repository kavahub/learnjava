package io.github.kavahub.learnjava.math;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FactorialHelperTest {
    static FactorialHelper helper;

    @BeforeAll
    public static void setup() {
        helper = new FactorialHelper();
    }

    @Test
    public void whenCalculatingFactorialUsingForLoop_thenCorrect() {
        int n = 5;

        assertThat(helper.usingForLoop(n)).isEqualTo(120);
    }

    @Test
    public void whenCalculatingFactorialUsingStreams_thenCorrect() {
        int n = 5;

        assertThat(helper.usingStreams(n)).isEqualTo(120);
    }

    @Test
    public void whenCalculatingFactorialUsingRecursion_thenCorrect() {
        int n = 5;

        assertThat(helper.usingRecursion(n)).isEqualTo(120);
    }

    @Test
    public void whenCalculatingFactorialUsingMemoize_thenCorrect() {
        int n = 5;

        assertThat(helper.usingMemoize(n)).isEqualTo(120);

        n = 6;

        assertThat(helper.usingMemoize(n)).isEqualTo(720);
    }

    @Test
    public void whenCalculatingFactorialHavingLargeResult_thenCorrect() {
        int n = 22;

        assertThat(helper.factorialHavingLargeResult(n)).isEqualTo(new BigInteger("1124000727777607680000"));
    }

    @Test
    public void whenCalculatingFactorialUsingApacheCommons_thenCorrect() {
        int n = 5;

        assertThat(helper.usingApacheCommons(n)).isEqualTo(120);
    }

    @Test
    public void whenCalculatingFactorialUsingGuava_thenCorrect() {
        int n = 22;

        assertThat(helper.usingGuava(n)).isEqualTo(new BigInteger("1124000727777607680000"));
    }   
}
