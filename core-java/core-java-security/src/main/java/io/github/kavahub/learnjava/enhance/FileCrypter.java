package io.github.kavahub.learnjava.enhance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 文件加解密
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
public class FileCrypter {
    private final SecretKey secretKey;
    private final Cipher cipher;

    /**
     * 
     * @param secretKey 密钥
     * @param cipher    算法
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     */
    FileCrypter(String key, String cipher) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.secretKey = new SecretKeySpec(key.getBytes(), "AES");
        this.cipher = Cipher.getInstance(cipher);
    }

    /**
     * 加密
     * 
     * @param content  加密内容
     * @param fileName
     * @throws InvalidKeyException
     * @throws IOException
     */
    public void encrypt(File sourceFile, File targetFile) throws InvalidKeyException, IOException {
        if (log.isDebugEnabled()) {
            log.debug("Encrypting file, source: {}, target: {}", sourceFile.getPath(), targetFile.getPath());
        }
        try (FileInputStream fileIn = new FileInputStream(sourceFile);
                FileOutputStream fileOut = new FileOutputStream(targetFile)) {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] iv = cipher.getIV();
            fileOut.write(iv);

            try (CipherInputStream cipherIn = new CipherInputStream(fileIn, cipher)) {
                copyByte(cipherIn, fileOut);
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("File encryption complete");
        }
    }

    /**
     * 
     * @param fileName
     * @return
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws IOException
     */
    public void decrypt(File sourceFile, File targetFile)
            throws InvalidAlgorithmParameterException, InvalidKeyException, IOException {
        if (log.isDebugEnabled()) {
            log.debug("Decrypting file, source: {}, target: {}", sourceFile.getPath(), targetFile.getPath());
        }
        try (FileInputStream fileIn = new FileInputStream(sourceFile);
                FileOutputStream fileOut = new FileOutputStream(targetFile)) {
            byte[] fileIv = new byte[16];
            fileIn.read(fileIv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(fileIv));

            try (CipherInputStream cipherIn = new CipherInputStream(fileIn, cipher)) {
                copyByte(cipherIn, fileOut);
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("File decryption complete");
        }

    }

    public void copyByte(InputStream is, OutputStream os) throws IOException {
        byte[] buf = new byte[1024];
        int bytesRead = 0;
        int bytesTotal = 0;
        while ((bytesRead = is.read(buf)) != -1) {
            os.write(buf, 0, bytesRead);
            bytesTotal += bytesRead;

            if (log.isDebugEnabled()) {
                log.debug("{} bytes transform complete", bytesTotal);
            }
        }
    }
}
