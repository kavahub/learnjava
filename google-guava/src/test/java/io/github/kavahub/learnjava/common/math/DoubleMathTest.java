package io.github.kavahub.learnjava.common.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.RoundingMode;

import com.google.common.math.DoubleMath;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link DoubleMath} 提供 <code>Double</code> 的实用方法
 *
 * <p>
 * {@link RoundingMode} 枚举：
 * 
 * <ul>
 * <li> CEILING：取右边最近的整数</li> 
 * <li> DOWN：去掉小数部分取整，也就是正数取左边，负数取右边，相当于向原点靠近的方向取整</li> 
 * <li> FLOOR：取左边最近的正数</li> 
 * <li> HALF_DOWN:五舍六入，负数先取绝对值再五舍六入再负数</li> 
 * <li> HALF_UP:四舍五入，负数原理同上</li> 
 * <li> HALF_EVEN:这个比较绕，整数位若是奇数则四舍五入，若是偶数则五舍六入</li> 
 * <li> UNNECESSARY: 无需舍位 </li> 
 * </ul>
 * 
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class DoubleMathTest {
    @Test
    public void whenFactorailDouble_shouldFactorialThemAndReturnTheResultIfInDoubleRange() {
        // 阶乘: n!=1×2×3×...×(n-1)×n
        double result = DoubleMath.factorial(5);
        assertEquals(120, result, 0);
    }

    @Test
    public void whenFactorailDouble_shouldFactorialThemAndReturnDoubkeInfIfNotInDoubletRange() {
        double result = DoubleMath.factorial(Integer.MAX_VALUE);
        assertEquals(Double.POSITIVE_INFINITY, result, 0);
    }

    @Test
    public void whenFuzzyCompareDouble_shouldReturnZeroIfInRange() {
        // 模糊比较：a -b <= tolerance
        int result = DoubleMath.fuzzyCompare(4, 4.05, 0.6);
        assertEquals(0, result);
    }

    @Test
    public void whenFuzzyCompareDouble_shouldReturnNonZeroIfNotInRange() {
        int result = DoubleMath.fuzzyCompare(4, 5, 0.1);
        assertEquals(-1, result);
    }

    @Test
    public void whenFuzzyEqualDouble_shouldReturnZeroIfInRange() {
        boolean result = DoubleMath.fuzzyEquals(4, 4.05, 0.6);
        assertTrue(result);
    }

    @Test
    public void whenFuzzyEqualDouble_shouldReturnNonZeroIfNotInRange() {
        boolean result = DoubleMath.fuzzyEquals(4, 5, 0.1);
        assertFalse(result);
    }

    @Test
    public void whenMathematicalIntDouble_shouldReturnTrueIfInRange() {
        // 判断这个数是一个有限数(非无穷)并且是一个精确的整数
        boolean result = DoubleMath.isMathematicalInteger(5);
        assertTrue(result);
    }

    @Test
    public void whenMathematicalIntDouble_shouldReturnFalseIfNotInRange() {
        boolean result = DoubleMath.isMathematicalInteger(5.2);
        assertFalse(result);
    }

    @Test
    public void whenIsPowerOfTwoDouble_shouldReturnTrueIfIsPowerOfTwo() {
        boolean result = DoubleMath.isMathematicalInteger(4);
        assertTrue(result);
    }

    @Test
    public void whenIsPowerOfTwoDouble_shouldReturnFalseIsNotPowerOfTwoe() {
        boolean result = DoubleMath.isMathematicalInteger(5.2);
        assertFalse(result);
    }

    @Test
    public void whenLog2Double_shouldReturnResult() {
        // 取 2 的 log 对数
        double result = DoubleMath.log2(4);
        assertEquals(2, result, 0);
    }

    @Test
    public void whenLog2DoubleValues_shouldLog2ThemAndReturnTheResultForCeilingRounding() {
        int result = DoubleMath.log2(30, RoundingMode.CEILING);
        assertEquals(5, result);
    }

    @Test
    public void whenLog2DoubleValues_shouldog2ThemAndReturnTheResultForFloorRounding() {
        int result = DoubleMath.log2(30, RoundingMode.FLOOR);
        assertEquals(4, result);
    }

    @Test
    public void whenLog2DoubleValues_shouldThrowArithmeticExceptionIfRoundingNotDefinedButNecessary() {
        assertThrows(ArithmeticException.class, () -> DoubleMath.log2(30, RoundingMode.UNNECESSARY));
    }
}
