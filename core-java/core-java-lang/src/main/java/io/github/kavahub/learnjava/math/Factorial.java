package io.github.kavahub.learnjava.math;

import java.math.BigInteger;
import java.util.stream.LongStream;

import com.google.common.math.BigIntegerMath;

import org.apache.commons.math3.util.CombinatoricsUtils;

import lombok.experimental.UtilityClass;

/**
 * 阶乘
 */
@UtilityClass
public class Factorial {
    public long usingForLoop(int n) {
        long fact = 1;
        for (int i = 2; i <= n; i++) {
            fact = fact * i;
        }
        return fact;
    }

    public long usingStreams(int n) {
        return LongStream.rangeClosed(1, n)
            .reduce(1, (long x, long y) -> x * y);
    }

    public long usingRecursion(int n) {
        if (n <= 2) {
            return n;
        }
        return n * usingRecursion(n - 1);
    }

    private Long[] factorials = new Long[20];

    public long usingMemoize(int n) {

        if (factorials[n] != null) {
            return factorials[n];
        }

        if (n <= 2) {
            return n;
        }
        long nthValue = n * usingMemoize(n - 1);
        factorials[n] = nthValue;
        return nthValue;
    }

    public BigInteger factorialHavingLargeResult(int n) {
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++)
            result = result.multiply(BigInteger.valueOf(i));
        return result;
    }

    public long usingApacheCommons(int n) {
        return CombinatoricsUtils.factorial(n);
    }

    public BigInteger usingGuava(int n) {
        return BigIntegerMath.factorial(n);
    }   
}
