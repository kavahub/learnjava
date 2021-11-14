package io.github.kavahub.learnjava;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

public class FileEncrypterDecrypterTest {
    private final static String ENCRYPTER_FILE = "baz.enc";

    @AfterAll
    public static void clearup() throws IOException {
        Files.deleteIfExists(Paths.get(ENCRYPTER_FILE));
    }

    @Test
    public void givenStringAndFilename_whenEncryptingIntoFile_andDecryptingFileAgain_thenOriginalStringIsReturned()
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException,
            InvalidAlgorithmParameterException {
        String originalContent = "foobar";
        SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();

        FileEncrypterDecrypter fileEncrypterDecrypter = new FileEncrypterDecrypter(secretKey, "AES/CBC/PKCS5Padding");
        fileEncrypterDecrypter.encrypt(originalContent, ENCRYPTER_FILE);

        String decryptedContent = fileEncrypterDecrypter.decrypt(ENCRYPTER_FILE);
        assertThat(decryptedContent, is(originalContent));
    }
}
