package io.github.kavahub.learnjava.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.util.ConversionClass.*;

/**
 * 
 * {@link ConversionClass} 应用示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ConversionClassTest {
    @Test
    void givenPasswordAndSalt_whenCreateSecreKeyCheckConversion_thenSuccess()
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        // given
        String password = "Hello@2021";
        String salt = "@$#Hello@#^$*";

        // when
        SecretKey encodedKey = getKeyFromPassword(password, salt);
        String encodedString = convertSecretKeyToString(encodedKey);
        SecretKey decodeKey = convertStringToSecretKey(encodedString);

        // then
        assertEquals(encodedKey, decodeKey);
    }

    @Test
    void givenSize_whenCreateSecreKeyCheckConversion_thenSuccess()
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        // given
        int size = 256;

        // when
        SecretKey encodedKey = generateKey(size);
        String encodedString = convertSecretKeyToString(encodedKey);
        SecretKey decodeKey = convertStringToSecretKey(encodedString);

        // then
        assertEquals(encodedKey, decodeKey);
    }   
}
