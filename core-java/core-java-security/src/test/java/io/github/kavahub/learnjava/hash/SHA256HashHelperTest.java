package io.github.kavahub.learnjava.hash;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SHA256HashHelperTest {
    private static String originalValue = "abc123";
    private static String hashedValue = "6ca13d52ca70c883e0f0bb101e425a89e8624de51db2d2392593af6a84118090";

    @Test
    public void testHashWithJavaMessageDigest() throws Exception {
        final String currentHashedValue = SHA256HashHelper.HashWithJavaMessageDigest(originalValue);
        assertEquals(hashedValue, currentHashedValue);
    }

    @Test
    public void testHashWithGuava() throws Exception {
        final String currentHashedValue = SHA256HashHelper.hashWithGuava(originalValue);
        assertEquals(hashedValue, currentHashedValue);
    }

    @Test
    public void testHashWithApacheCommans() throws Exception {
        final String currentHashedValue = SHA256HashHelper.HashWithApacheCommons(originalValue);
        assertEquals(hashedValue, currentHashedValue);
    }

    @Test
    public void testHashWithBouncyCastle() throws Exception {
        final String currentHashedValue = SHA256HashHelper.HashWithBouncyCastle(originalValue);
        assertEquals(hashedValue, currentHashedValue);
    }   
}
