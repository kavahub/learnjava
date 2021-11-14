package io.github.kavahub.learnjava.hash;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PBKDF2HashHelperTest {
    String message = "this is a message";
    private static PBKDF2HashHelper pbkdf2;

    @BeforeAll
    public static void setUp() throws Exception {
        pbkdf2 = new PBKDF2HashHelper();
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
