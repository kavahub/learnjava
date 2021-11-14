package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

public class AESHelperTest implements WithAssertions {

    @Test
    void givenString_whenEncrypt_thenSuccess() throws NoSuchAlgorithmException, IllegalBlockSizeException,
            InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {
        // given
        String input = "learnjava";
        SecretKey key = AESHelper.generateKey(128);
        IvParameterSpec ivParameterSpec = AESHelper.generateIv();
        String algorithm = "AES/CBC/PKCS5Padding";

        // when
        String cipherText = AESHelper.encrypt(algorithm, input, key, ivParameterSpec);
        String plainText = AESHelper.decrypt(algorithm, cipherText, key, ivParameterSpec);

        // then
        assertEquals(input, plainText);
    }

    @Test
    void givenFile_whenEncrypt_thenSuccess() throws NoSuchAlgorithmException, IOException, IllegalBlockSizeException,
            InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {
        // given
        SecretKey key = AESHelper.generateKey(128);
        String algorithm = "AES/CBC/PKCS5Padding";
        IvParameterSpec ivParameterSpec = AESHelper.generateIv();
        File inputFile = Paths.get("src/test/resources/learnjava.txt").toFile();
        File encryptedFile = new File("learnjava.encrypted");
        File decryptedFile = new File("document.decrypted");

        // when
        AESHelper.encryptFile(algorithm, key, ivParameterSpec, inputFile, encryptedFile);
        AESHelper.decryptFile(algorithm, key, ivParameterSpec, encryptedFile, decryptedFile);

        // then
        assertThat(inputFile).hasSameTextualContentAs(decryptedFile);
        encryptedFile.delete();
        decryptedFile.delete();
    }

    @Test
    void givenObject_whenEncrypt_thenSuccess() throws NoSuchAlgorithmException, IllegalBlockSizeException,
            InvalidKeyException, InvalidAlgorithmParameterException, NoSuchPaddingException, IOException,
            BadPaddingException, ClassNotFoundException {
        // given
        Student student = new Student("Jack", 20);
        SecretKey key = AESHelper.generateKey(128);
        IvParameterSpec ivParameterSpec = AESHelper.generateIv();
        String algorithm = "AES/CBC/PKCS5Padding";

        // when
        SealedObject sealedObject = AESHelper.encryptObject(algorithm, student, key, ivParameterSpec);
        Student object = (Student) AESHelper.decryptObject(algorithm, sealedObject, key, ivParameterSpec);

        // then
        assertThat(student).isEqualTo(object);
    }

    @Test
    void givenPassword_whenEncrypt_thenSuccess()
            throws InvalidKeySpecException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {
        // given
        String plainText = "learnjava.net";
        String password = "learnjava";
        String salt = "12345678";
        IvParameterSpec ivParameterSpec = AESHelper.generateIv();
        SecretKey key = AESHelper.getKeyFromPassword(password, salt);

        // when
        String cipherText = AESHelper.encryptPasswordBased(plainText, key, ivParameterSpec);
        String decryptedCipherText = AESHelper.decryptPasswordBased(cipherText, key, ivParameterSpec);

        // then
        assertEquals(plainText, decryptedCipherText);
    }
}
