package io.github.kavahub.learnjava.counter;

import java.math.BigInteger;

import lombok.experimental.UtilityClass;

/**
 * 
 * 数学工具
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class MathUtils {
    public BigInteger factorial(int number) {
        BigInteger f = new BigInteger("1");
        for (int i = 2; i <= number; i++) {
            f = f.multiply(BigInteger.valueOf(i));
        }
        return f;
    }
}
