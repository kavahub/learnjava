package io.github.kavahub.learnjava.hash;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SHA3HashTest {
    private static String originalValue = "abc123";
    private static String hashedValue = "f58fa3df820114f56e1544354379820cff464c9c41cb3ca0ad0b0843c9bb67ee";

    /* works with JDK9+ only */
    //@Test
    public void testHashWithJavaMessageDigestJDK9() throws Exception {
        final String currentHashedValue = SHA3HashHelper.hashWithJavaMessageDigestJDK9(originalValue);
        assertEquals(hashedValue, currentHashedValue);
    }

    @Test
    public void testHashWithJavaMessageDigest() throws Exception {
        final String currentHashedValue = SHA3HashHelper.hashWithJavaMessageDigest(originalValue);
        assertEquals(hashedValue, currentHashedValue);
    }

    /* works with JDK9+ only */
    @Test
    public void testHashWithApacheCommonsJDK9() {
        final String currentHashedValue = SHA3HashHelper.hashWithApacheCommonsJDK9(originalValue);
        assertEquals(hashedValue, currentHashedValue);
    }

    @Test
    public void testHashWithBouncyCastle() {
        final String currentHashedValue = SHA3HashHelper.hashWithBouncyCastle(originalValue);
        assertEquals(hashedValue, currentHashedValue);
    }    
}
