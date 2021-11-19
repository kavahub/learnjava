package io.github.kavahub.learnjava.enhance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.enhance.PKCS8PemReader.*;

public class PKCS8PemReaderTest {
    @Test
    public void whenReadPublicKeyFromPEMFile_thenSuccess() throws Exception {
        File pemFile = new File(PKCS8PemReaderTest.class.getResource("/pem/public-key.pem").getFile());

        RSAPublicKey publicKey = readX509PublicKey(pemFile);

        assertEquals("X.509", publicKey.getFormat());
        assertEquals("RSA", publicKey.getAlgorithm());
    }

    @Test
    public void whenReadPrivateKeyFromPEMFile_thenSuccess() throws Exception {
        File pemFile = new File(PKCS8PemReaderTest.class.getResource("/pem/private-key-pkcs8.pem").getFile());

        RSAPrivateKey privateKey = readPKCS8PrivateKey(pemFile);

        assertEquals("PKCS#8", privateKey.getFormat());
        assertEquals("RSA", privateKey.getAlgorithm());
    }  
}
