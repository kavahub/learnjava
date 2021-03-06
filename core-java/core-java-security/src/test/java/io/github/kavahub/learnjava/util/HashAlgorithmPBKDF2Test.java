package io.github.kavahub.learnjava.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * 
 * {@link HashAlgorithmPBKDF2} 应用示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class HashAlgorithmPBKDF2Test {
    String message = "this is a message";
    private static HashAlgorithmPBKDF2 pbkdf2;

    @BeforeAll
    public static void setUp() throws Exception {
        pbkdf2 = new HashAlgorithmPBKDF2();
    }

    @Test
    public void givenCorrectMessageAndHash_whenAuthenticated_checkAuthenticationSucceeds() throws Exception {
        String hash1 = pbkdf2.hash(message.toCharArray());

        assertTrue(pbkdf2.checkPassword(message.toCharArray(), hash1));
        System.out.println(hash1);
    }

    @Test
    public void givenWrongMessage_whenAuthenticated_checkAuthenticationFails() throws Exception {
        String hash1 = pbkdf2.hash(message.toCharArray());

        String wrongPasswordAttempt = "IamWrong";

        assertFalse(pbkdf2.checkPassword(wrongPasswordAttempt.toCharArray(), hash1));
        System.out.println(hash1);
    }

}
