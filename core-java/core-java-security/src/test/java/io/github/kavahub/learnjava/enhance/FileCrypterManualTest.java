package io.github.kavahub.learnjava.enhance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileCrypterManualTest {
    private final static String KEY = "1234567890123456";
    private final static Path SOURCE_ENCRYPTER_FILE = Paths.get("src", "test", "resources", "sourceEncrypterFile.txt");
    private final static Path TARGET_DECRYPTER_FILE = Paths.get("target", "targetDecrypterFile.txt");

    private final static Path SOURCE_DECRYPTER_FILE = Paths.get("src", "test", "resources", "sourceDecrypterFile.txt");
    private final static Path TARGET_ENCRYPTER_FILE = Paths.get("target", "targetEncrypterFile.txt");
    @BeforeAll
    public static void clearup() throws IOException {
        Files.deleteIfExists(TARGET_DECRYPTER_FILE);
        Files.deleteIfExists(TARGET_ENCRYPTER_FILE);
    }

    @Test
    public void givenSourceAndTarget_thenEncrypt()
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException,
            InvalidAlgorithmParameterException {

        FileCrypter fileCrypter = new FileCrypter(KEY, "AES/CBC/PKCS5Padding");
        fileCrypter.encrypt(SOURCE_ENCRYPTER_FILE.toFile(), TARGET_DECRYPTER_FILE.toFile());
    }

    @Test
    public void givenSourceAndTarget_thenDecrypt()
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException,
            InvalidAlgorithmParameterException {

        FileCrypter fileCrypter = new FileCrypter(KEY, "AES/CBC/PKCS5Padding");
        fileCrypter.decrypt(SOURCE_DECRYPTER_FILE.toFile(), TARGET_ENCRYPTER_FILE.toFile());
    }
}
