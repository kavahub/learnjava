package io.github.kavahub.learnjava.common.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.RoundingMode;

import com.google.common.math.IntMath;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link IntMath} 提供 <code>Integer</code> 的实用方法
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class IntMathTest {
    @Test
    public void whenPerformBinomialOnTwoIntegerValues_shouldReturnResultIfUnderInt() {
        int result = IntMath.binomial(6, 3);
        assertEquals(20, result);
    }

    @Test
    public void whenPerformBinomialOnTwoIntegerValues_shouldReturnIntMaxIfUnderInt() {
        int result = IntMath.binomial(Integer.MAX_VALUE, 3);
        assertEquals(Integer.MAX_VALUE, result);
    }

    @Test
    public void whenProformCeilPowOfTwoIntegerValues_shouldReturnResult() {
        int result = IntMath.ceilingPowerOfTwo(20);
        assertEquals(32, result);
    }

    @Test
    public void whenCheckedAddTwoIntegerValues_shouldAddThemAndReturnTheSumIfNotOverflow() {
        // a + b
        int result = IntMath.checkedAdd(1, 2);
        assertEquals(3, result);
    }

    @Test
    public void gwhenCheckedAddTwoIntegerValues_shouldThrowArithmeticExceptionIfOverflow() {
        assertThrows(ArithmeticException.class, () -> IntMath.checkedAdd(Integer.MAX_VALUE, 100));
    }

    @Test
    public void whenCheckedMultiplyTwoIntegerValues_shouldMultiplyThemAndReturnTheResultIfNotOverflow() {
        // a * b
        int result = IntMath.checkedMultiply(1, 2);
        assertEquals(2, result);
    }

    @Test
    public void gwhenCheckedMultiplyTwoIntegerValues_shouldThrowArithmeticExceptionIfOverflow() {
        assertThrows(ArithmeticException.class, () -> IntMath.checkedMultiply(Integer.MAX_VALUE, 100));
    }

    @Test
    public void whenCheckedPowTwoIntegerValues_shouldPowThemAndReturnTheResultIfNotOverflow() {
        // a的b次方
        int result = IntMath.checkedPow(2, 3);
        assertEquals(8, result);
    }

    @Test
    public void gwhenCheckedPowTwoIntegerValues_shouldThrowArithmeticExceptionIfOverflow() {
        assertThrows(ArithmeticException.class, () -> IntMath.checkedPow(Integer.MAX_VALUE, 100));
    }

    @Test
    public void whenCheckedSubstractTwoIntegerValues_shouldSubstractThemAndReturnTheResultIfNotOverflow() {
        // a - b
        int result = IntMath.checkedSubtract(4, 1);
        assertEquals(3, result);
    }

    @Test
    public void gwhenCheckedSubstractTwoIntegerValues_shouldThrowArithmeticExceptionIfOverflow() {
        assertThrows(ArithmeticException.class, () -> IntMath.checkedSubtract(Integer.MAX_VALUE, -100));
    }

    @Test
    public void whenDivideTwoIntegerValues_shouldDivideThemAndReturnTheResultForCeilingRounding() {
        // a / b
        int result = IntMath.divide(10, 3, RoundingMode.CEILING);
        assertEquals(4, result);
    }

    @Test
    public void whenDivideTwoIntegerValues_shouldDivideThemAndReturnTheResultForFloorRounding() {
        int result = IntMath.divide(10, 3, RoundingMode.FLOOR);
        assertEquals(3, result);
    }

    @Test
    public void whenDivideTwoIntegerValues_shouldThrowArithmeticExceptionIfRoundingNotDefinedButNecessary() {
        assertThrows(ArithmeticException.class, () -> IntMath.divide(10, 3, RoundingMode.UNNECESSARY));
    }

    @Test
    public void whenFactorailInteger_shouldFactorialThemAndReturnTheResultIfInIntRange() {
        int result = IntMath.factorial(5);
        assertEquals(120, result);
    }

    @Test
    public void whenFactorailInteger_shouldFactorialThemAndReturnIntMaxIfNotInIntRange() {
        int result = IntMath.factorial(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, result);
    }

    @Test
    public void whenFloorPowerOfInteger_shouldReturnValue() {
        int result = IntMath.floorPowerOfTwo(30);
        assertEquals(16, result);
    }

    @Test
    public void whenGcdOfTwoIntegers_shouldReturnValue() {
        int result = IntMath.gcd(30, 40);
        assertEquals(10, result);
    }

    @Test
    public void whenIsPowOfInteger_shouldReturnTrueIfPowerOfTwo() {
        boolean result = IntMath.isPowerOfTwo(16);
        assertTrue(result);
    }

    @Test
    public void whenIsPowOfInteger_shouldReturnFalseeIfNotPowerOfTwo() {
        boolean result = IntMath.isPowerOfTwo(20);
        assertFalse(result);
    }

    @Test
    public void whenIsPrineOfInteger_shouldReturnFalseeIfNotPrime() {
        boolean result = IntMath.isPrime(20);
        assertFalse(result);
    }

    @Test
    public void whenLog10IntegerValues_shouldLog10ThemAndReturnTheResultForCeilingRounding() {
        int result = IntMath.log10(30, RoundingMode.CEILING);
        assertEquals(2, result);
    }

    @Test
    public void whenLog10IntegerValues_shouldog10ThemAndReturnTheResultForFloorRounding() {
        int result = IntMath.log10(30, RoundingMode.FLOOR);
        assertEquals(1, result);
    }

    @Test
    public void whenLog10IntegerValues_shouldThrowArithmeticExceptionIfRoundingNotDefinedButNecessary() {
        assertThrows(ArithmeticException.class, () -> IntMath.log10(30, RoundingMode.UNNECESSARY));
    }

    @Test
    public void whenLog2IntegerValues_shouldLog2ThemAndReturnTheResultForCeilingRounding() {
        int result = IntMath.log2(30, RoundingMode.CEILING);
        assertEquals(5, result);
    }

    @Test
    public void whenLog2IntegerValues_shouldog2ThemAndReturnTheResultForFloorRounding() {
        int result = IntMath.log2(30, RoundingMode.FLOOR);
        assertEquals(4, result);
    }

    @Test
    public void whenLog2IntegerValues_shouldThrowArithmeticExceptionIfRoundingNotDefinedButNecessary() {
        assertThrows(ArithmeticException.class, () -> IntMath.log2(30, RoundingMode.UNNECESSARY));
    }

    @Test
    public void whenMeanTwoIntegerValues_shouldMeanThemAndReturnTheResult() {
        // 算术平均数
        int result = IntMath.mean(30, 20);
        assertEquals(25, result);
    }

    @Test
    public void whenModTwoIntegerValues_shouldModThemAndReturnTheResult() {
        // 取模，也就是余数
        int result = IntMath.mod(30, 4);
        assertEquals(2, result);
    }

    @Test
    public void whenPowTwoIntegerValues_shouldPowThemAndReturnTheResult() {
        // 6 的 4 次方
        int result = IntMath.pow(6, 4);
        assertEquals(1296, result);
    }

    @Test
    public void whenSaturatedAddTwoIntegerValues_shouldAddThemAndReturnTheResult() {
        // 加
        int result = IntMath.saturatedAdd(6, 4);
        assertEquals(10, result);
    }

    @Test
    public void whenSaturatedAddTwoIntegerValues_shouldAddThemAndReturnIntMaxIfOverflow() {
        int result = IntMath.saturatedAdd(Integer.MAX_VALUE, 1000);
        assertEquals(Integer.MAX_VALUE, result);
    }

    @Test
    public void whenSaturatedAddTwoIntegerValues_shouldAddThemAndReturnIntMinIfUnderflow() {
        int result = IntMath.saturatedAdd(Integer.MIN_VALUE, -1000);
        assertEquals(Integer.MIN_VALUE, result);
    }

    @Test
    public void whenSaturatedMultiplyTwoIntegerValues_shouldMultiplyThemAndReturnTheResult() {
        // 乘
        int result = IntMath.saturatedMultiply(6, 4);
        assertEquals(24, result);
    }

    @Test
    public void whenSaturatedMultiplyTwoIntegerValues_shouldMultiplyThemAndReturnIntMaxIfOverflow() {
        int result = IntMath.saturatedMultiply(Integer.MAX_VALUE, 1000);
        assertEquals(Integer.MAX_VALUE, result);
    }

    @Test
    public void whenSaturatedMultiplyTwoIntegerValues_shouldMultiplyThemAndReturnIntMinIfUnderflow() {
        int result = IntMath.saturatedMultiply(Integer.MIN_VALUE, 1000);
        assertEquals(Integer.MIN_VALUE, result);
    }

    @Test
    public void whenSaturatedPowTwoIntegerValues_shouldPowThemAndReturnTheResult() {
        // 次方
        int result = IntMath.saturatedPow(6, 2);
        assertEquals(36, result);
    }

    @Test
    public void whenSaturatedPowTwoIntegerValues_shouldPowThemAndReturnIntMaxIfOverflow() {
        int result = IntMath.saturatedPow(Integer.MAX_VALUE, 2);
        assertEquals(Integer.MAX_VALUE, result);
    }

    @Test
    public void whenSaturatedPowTwoIntegerValues_shouldPowThemAndReturnIntMinIfUnderflow() {
        int result = IntMath.saturatedPow(Integer.MIN_VALUE, 3);
        assertEquals(Integer.MIN_VALUE, result);
    }

    @Test
    public void whenSaturatedSubstractTwoIntegerValues_shouldSubstractThemAndReturnTheResult() {
        // 减
        int result = IntMath.saturatedSubtract(6, 2);
        assertEquals(4, result);
    }

    @Test
    public void whenSaturatedSubstractTwoIntegerValues_shouldSubstractwThemAndReturnIntMaxIfOverflow() {
        int result = IntMath.saturatedSubtract(Integer.MAX_VALUE, -2);
        assertEquals(Integer.MAX_VALUE, result);
    }

    @Test
    public void whenSaturatedSubstractTwoIntegerValues_shouldSubstractThemAndReturnIntMinIfUnderflow() {
        int result = IntMath.saturatedSubtract(Integer.MIN_VALUE, 3);
        assertEquals(Integer.MIN_VALUE, result);
    }

    @Test
    public void whenSqrtIntegerValues_shouldSqrtThemAndReturnTheResultForCeilingRounding() {
        // 开方
        int result = IntMath.sqrt(30, RoundingMode.CEILING);
        assertEquals(6, result);
    }

    @Test
    public void whenSqrtIntegerValues_shouldSqrtThemAndReturnTheResultForFloorRounding() {
        int result = IntMath.sqrt(30, RoundingMode.FLOOR);
        assertEquals(5, result);
    }

    @Test
    public void whenSqrtIntegerValues_shouldThrowArithmeticExceptionIfRoundingNotDefinedButNecessary() {
        assertThrows(ArithmeticException.class, () -> IntMath.sqrt(30, RoundingMode.UNNECESSARY));
    }

}
