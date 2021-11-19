package io.github.kavahub.learnjava.enhance;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;

import lombok.experimental.UtilityClass;

/**
 * 公钥，私钥读取，Pem格式文件
 * 
 * <p>
 * PKCS8是对加密后的秘钥进行了描述
 * 
 * <p>
 * PEM与DER有什么区别呢？其实PEM就是对DER的内容做了base64的编码并做了一下格式化的输出而已。DER格式的存储使用了一种叫asn1的数据存储格式来存储各个数据项。
 */
@UtilityClass
public class PKCS8PemReader {
    public RSAPrivateKey readPKCS8PrivateKey(File file) throws GeneralSecurityException, IOException {
        String key = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());

        String privateKeyPEM = key.replace("-----BEGIN PRIVATE KEY-----", "").replaceAll(System.lineSeparator(), "")
                .replace("-----END PRIVATE KEY-----", "");

        byte[] encoded = Base64.decodeBase64(privateKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }

    public RSAPublicKey readX509PublicKey(File file) throws GeneralSecurityException, IOException {
        String key = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());

        String publicKeyPEM = key.replace("-----BEGIN PUBLIC KEY-----", "").replaceAll(System.lineSeparator(), "")
                .replace("-----END PUBLIC KEY-----", "");

        byte[] encoded = Base64.decodeBase64(publicKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

}
