package io.github.kavahub.learnjava.common.cache;

import java.math.BigInteger;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * 
 * Factorial(阶乘), 使用缓存实现
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class FactorialSequence {
    private static LoadingCache<Integer, BigInteger> memo = CacheBuilder.newBuilder()
            .build(CacheLoader.from(FactorialSequence::getFactorial));

    public static BigInteger getFactorial(int n) {
        if (n == 0) {
            return BigInteger.ONE;
        } else {
            return BigInteger.valueOf(n).multiply(memo.getUnchecked(n - 1));
        }
    }
}
