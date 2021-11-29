package io.github.kavahub.learnjava.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.util.HashAlgorithmKeccak256.*;

/**
 * 
 * {@link HashAlgorithmKeccak256} 应用示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class HashAlgorithmKeccak256Test {
    private static String originalValue = "abc123";
    private static String hashedValue = "719accc61a9cc126830e5906f9d672d06eab6f8597287095a2c55a8b775e7016";

    @Test
    public void testHashWithJavaMessageDigest() throws Exception {
        final String currentHashedValue = hashWithJavaMessageDigest(originalValue);
        assertEquals(hashedValue, currentHashedValue);
    }

    @Test
    public void testHashWithBouncyCastle() {
        final String currentHashedValue = hashWithBouncyCastle(originalValue);
        assertEquals(hashedValue, currentHashedValue);
    }
}
