package io.github.kavahub.learnjava.common.cache;

import java.math.BigInteger;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

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
