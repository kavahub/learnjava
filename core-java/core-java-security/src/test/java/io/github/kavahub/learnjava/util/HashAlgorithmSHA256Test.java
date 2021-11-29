package io.github.kavahub.learnjava.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.util.HashAlgorithmSHA256.*;

/**
 * 
 * {@link HashAlgorithmSHA256} 应用示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class HashAlgorithmSHA256Test {
    private static String originalValue = "abc123";
    private static String hashedValue = "6ca13d52ca70c883e0f0bb101e425a89e8624de51db2d2392593af6a84118090";

    @Test
    public void testHashWithJavaMessageDigest() throws Exception {
        final String currentHashedValue = hashWithJavaMessageDigest(originalValue);
        assertEquals(hashedValue, currentHashedValue);
    }

    @Test
    public void testHashWithGuava() throws Exception {
        final String currentHashedValue = hashWithGuava(originalValue);
        assertEquals(hashedValue, currentHashedValue);
    }

    @Test
    public void testHashWithApacheCommans() throws Exception {
        final String currentHashedValue = hashWithApacheCommons(originalValue);
        assertEquals(hashedValue, currentHashedValue);
    }

    @Test
    public void testHashWithBouncyCastle() throws Exception {
        final String currentHashedValue = hashWithBouncyCastle(originalValue);
        assertEquals(hashedValue, currentHashedValue);
    }   
}
