package io.github.kavahub.learnjava.hash;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Keccak256HashHelperTest {
    private static String originalValue = "abc123";
    private static String hashedValue = "719accc61a9cc126830e5906f9d672d06eab6f8597287095a2c55a8b775e7016";

    @Test
    public void testHashWithJavaMessageDigest() throws Exception {
        final String currentHashedValue = Keccak256HashHelper.hashWithJavaMessageDigest(originalValue);
        assertEquals(hashedValue, currentHashedValue);
    }

    @Test
    public void testHashWithBouncyCastle() {
        final String currentHashedValue = Keccak256HashHelper.hashWithBouncyCastle(originalValue);
        assertEquals(hashedValue, currentHashedValue);
    }
}
