package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Test;

public class ConversionClassHelperTest {
    @Test
    void givenPasswordAndSalt_whenCreateSecreKeyCheckConversion_thenSuccess()
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        // given
        String password = "Hello@2021";
        String salt = "@$#Hello@#^$*";

        // when
        SecretKey encodedKey = ConversionClassHelper.getKeyFromPassword(password, salt);
        String encodedString = ConversionClassHelper.convertSecretKeyToString(encodedKey);
        SecretKey decodeKey = ConversionClassHelper.convertStringToSecretKeyto(encodedString);

        // then
        assertEquals(encodedKey, decodeKey);
    }

    @Test
    void givenSize_whenCreateSecreKeyCheckConversion_thenSuccess()
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        // given
        int size = 256;

        // when
        SecretKey encodedKey = ConversionClassHelper.generateKey(size);
        String encodedString = ConversionClassHelper.convertSecretKeyToString(encodedKey);
        SecretKey decodeKey = ConversionClassHelper.convertStringToSecretKeyto(encodedString);

        // then
        assertEquals(encodedKey, decodeKey);
    }   
}
