package io.github.kavahub.learnjava;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;


/**
 * 
 * {@link SecureRandom} 使用示例
 * 
 * <p>
 * {@code SecureRandom} 是强随机数生成器，主要应用的场景为：用于安全目的的数据，例如生成秘钥或者会话标示（session
 * ID）。使用强随机数生成器将会极大的降低出问题的风险
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class SecureRandomExample {

    public static void main(String[] args) {
        SecureRandom sr = new SecureRandom();

        System.out.println("nextInt: " + sr.nextInt());
        System.out.println("nextLong: " + sr.nextLong());
        System.out.println("nextFloat: " + sr.nextFloat());
        System.out.println("nextDouble: " + sr.nextDouble());
        System.out.println("nextBoolean: " + sr.nextBoolean());

        System.out.println("ints: ");
        IntStream randomIntStream = sr.ints();
        randomIntStream.limit(10).forEach(e -> System.out.println(e));

        System.out.println("longs: ");
        LongStream randomLongStream = sr.longs();
        randomLongStream.limit(10).forEach(e -> System.out.println(e));

        System.out.println("doubles: ");
        DoubleStream randomDoubleStream = sr.doubles();
        randomDoubleStream.limit(10).forEach(e -> System.out.println(e));

        byte[] values = new byte[124];
        sr.nextBytes(values);
    }

    /**
     * 指定算法创建
     * 
     * @param algorithm 算法
     * @return
     * @throws NoSuchAlgorithmException
     */
    public SecureRandom getSecureRandomForAlgorithm(String algorithm) throws NoSuchAlgorithmException {
        if (algorithm == null || algorithm.isEmpty()) {
            return new SecureRandom();
        }

        return SecureRandom.getInstance(algorithm);
    }

}
