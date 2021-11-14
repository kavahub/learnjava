package io.github.kavahub.learnjava.common.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigInteger;
import java.math.RoundingMode;

import com.google.common.math.BigIntegerMath;

import org.junit.jupiter.api.Test;

public class BigIntegerMathTest {
    @Test
    public void whenPerformBinomialOnTwoIntValues_shouldReturnResult() {
        // 二项式
        BigInteger result = BigIntegerMath.binomial(6, 3);
        assertEquals(new BigInteger("20"), result);
    }

    @Test
    public void whenProformCeilPowOfTwoBigIntegerValues_shouldReturnResult() {
        // 大於或等於x的2的最小冪
        BigInteger result = BigIntegerMath.ceilingPowerOfTwo(new BigInteger("20"));
        assertEquals(new BigInteger("32"), result);
    }

    @Test
    public void whenDivideTwoBigIntegerValues_shouldDivideThemAndReturnTheResultForCeilingRounding() {
        // 除法
        BigInteger result = BigIntegerMath.divide(new BigInteger("10"), new BigInteger("3"), RoundingMode.CEILING);
        assertEquals(new BigInteger("4"), result);
    }

    @Test
    public void whenDivideTwoBigIntegerValues_shouldDivideThemAndReturnTheResultForFloorRounding() {
        BigInteger result = BigIntegerMath.divide(new BigInteger("10"), new BigInteger("3"), RoundingMode.FLOOR);
        assertEquals(new BigInteger("3"), result);
    }

    @Test
    public void whenDivideTwoBigIntegerValues_shouldThrowArithmeticExceptionIfRoundingNotDefinedButNecessary() {
        assertThrows(ArithmeticException.class,
                () -> BigIntegerMath.divide(new BigInteger("10"), new BigInteger("3"), RoundingMode.UNNECESSARY));
    }

    @Test
    public void whenFactorailInteger_shouldFactorialThemAndReturnTheResultIfInIntRange() {
        BigInteger result = BigIntegerMath.factorial(5);
        assertEquals(new BigInteger("120"), result);
    }

    @Test
    public void whenFloorPowerOfInteger_shouldReturnValue() {
        // 小于或等于x的2的最大幂
        BigInteger result = BigIntegerMath.floorPowerOfTwo(new BigInteger("30"));
        assertEquals(new BigInteger("16"), result);
    }

    @Test
    public void whenIsPowOfInteger_shouldReturnTrueIfPowerOfTwo() {
        // 校验一个整数是不是2的指数
        boolean result = BigIntegerMath.isPowerOfTwo(new BigInteger("16"));
        assertTrue(result);
    }

    @Test
    public void whenLog10BigIntegerValues_shouldLog10ThemAndReturnTheResultForCeilingRounding() {
        int result = BigIntegerMath.log10(new BigInteger("30"), RoundingMode.CEILING);
        assertEquals(2, result);
    }

    @Test
    public void whenLog10BigIntegerValues_shouldog10ThemAndReturnTheResultForFloorRounding() {
        int result = BigIntegerMath.log10(new BigInteger("30"), RoundingMode.FLOOR);
        assertEquals(1, result);
    }

    @Test
    public void whenLog10BigIntegerValues_shouldThrowArithmeticExceptionIfRoundingNotDefinedButNecessary() {
        assertThrows(ArithmeticException.class, () -> BigIntegerMath.log10(new BigInteger("30"), RoundingMode.UNNECESSARY));
    }

    @Test
    public void whenLog2BigIntegerValues_shouldLog2ThemAndReturnTheResultForCeilingRounding() {
        int result = BigIntegerMath.log2(new BigInteger("30"), RoundingMode.CEILING);
        assertEquals(5, result);
    }

    @Test
    public void whenLog2BigIntegerValues_shouldog2ThemAndReturnTheResultForFloorRounding() {
        int result = BigIntegerMath.log2(new BigInteger("30"), RoundingMode.FLOOR);
        assertEquals(4, result);
    }

    @Test
    public void whenLog2BigIntegerValues_shouldThrowArithmeticExceptionIfRoundingNotDefinedButNecessary() {
        assertThrows(ArithmeticException.class, () -> BigIntegerMath.log2(new BigInteger("30"), RoundingMode.UNNECESSARY));
    }

    @Test
    public void whenSqrtBigIntegerValues_shouldSqrtThemAndReturnTheResultForCeilingRounding() {
        // 开根
        BigInteger result = BigIntegerMath.sqrt(new BigInteger("30"), RoundingMode.CEILING);
        assertEquals(new BigInteger("6"), result);
    }

    @Test
    public void whenSqrtBigIntegerValues_shouldSqrtThemAndReturnTheResultForFloorRounding() {
        BigInteger result = BigIntegerMath.sqrt(new BigInteger("30"), RoundingMode.FLOOR);
        assertEquals(new BigInteger("5"), result);
    }

    @Test
    public void whenSqrtBigIntegerValues_shouldThrowArithmeticExceptionIfRoundingNotDefinedButNecessary() {
        assertThrows(ArithmeticException.class, () -> BigIntegerMath.sqrt(new BigInteger("30"), RoundingMode.UNNECESSARY));
    }
}
