package io.github.kavahub.learnjava.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.util.HashAlgorithmSHA3.*;

public class HashAlgorithmSHA3Test {
    private static String originalValue = "abc123";
    private static String hashedValue = "f58fa3df820114f56e1544354379820cff464c9c41cb3ca0ad0b0843c9bb67ee";

    /* works with JDK9+ only */
    //@Test
    public void testHashWithJavaMessageDigestJDK9() throws Exception {
        final String currentHashedValue = hashWithJavaMessageDigestJDK9(originalValue);
        assertEquals(hashedValue, currentHashedValue);
    }

    @Test
    public void testHashWithJavaMessageDigest() throws Exception {
        final String currentHashedValue = hashWithJavaMessageDigest(originalValue);
        assertEquals(hashedValue, currentHashedValue);
    }

    /* works with JDK9+ only */
    @Test
    public void testHashWithApacheCommonsJDK9() {
        final String currentHashedValue = hashWithApacheCommonsJDK9(originalValue);
        assertEquals(hashedValue, currentHashedValue);
    }

    @Test
    public void testHashWithBouncyCastle() {
        final String currentHashedValue = hashWithBouncyCastle(originalValue);
        assertEquals(hashedValue, currentHashedValue);
    }    
}
