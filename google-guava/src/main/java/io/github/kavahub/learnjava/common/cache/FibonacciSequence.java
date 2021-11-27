package io.github.kavahub.learnjava.common.cache;

import java.math.BigInteger;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;


/**
 * 
 * Fibonacci(菲波那契数列)，使用缓存实现
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class FibonacciSequence {
    private static LoadingCache<Integer, BigInteger> memo = CacheBuilder.newBuilder()
            .maximumSize(100)
            .build(CacheLoader.from(FibonacciSequence::getFibonacciNumber));

    public static BigInteger getFibonacciNumber(int n) {
        if (n == 0) {
            return BigInteger.ZERO;
        } else if (n == 1) {
            return BigInteger.ONE;
        } else {
            return memo.getUnchecked(n - 1).add(memo.getUnchecked(n - 2));
        }
    }
}
