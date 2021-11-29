package io.github.kavahub.learnjava.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import lombok.experimental.UtilityClass;

/**
 * 
 * 公钥加密，私钥解密. 使用<code>RSA/ECB/PKCS1Padding</code>算法
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class CertificateCryptor {
    /**
     * 加密， 
     * 
     * @param message              消息
     * @param publicKeyCertificate 公钥
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public byte[] encryptMessage(byte[] message, Certificate publicKey) throws InvalidKeyException,
            NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedMessage = cipher.doFinal(message);
        return encryptedMessage;
    }

    /**
     * 解密
     * 
     * @param message 消息
     * @param privateKey 私钥
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public byte[] decryptMessage(byte[] message, Certificate privateKey) throws InvalidKeyException,
            NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] encryptedMessage = cipher.doFinal(message);
        return encryptedMessage;
    }
}
