package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.junit.jupiter.api.Test;

public class JavaSecurityPemHelperTest {
    @Test
    public void whenReadPublicKeyFromPEMFile_thenSuccess() throws Exception {
        File pemFile = new File(JavaSecurityPemHelperTest.class.getResource("/pem/public-key.pem").getFile());

        RSAPublicKey publicKey = JavaSecurityPemHelper.readX509PublicKey(pemFile);

        assertEquals("X.509", publicKey.getFormat());
        assertEquals("RSA", publicKey.getAlgorithm());
    }

    @Test
    public void whenReadPrivateKeyFromPEMFile_thenSuccess() throws Exception {
        File pemFile = new File(JavaSecurityPemHelperTest.class.getResource("/pem/private-key-pkcs8.pem").getFile());

        RSAPrivateKey privateKey = JavaSecurityPemHelper.readPKCS8PrivateKey(pemFile);

        assertEquals("PKCS#8", privateKey.getFormat());
        assertEquals("RSA", privateKey.getAlgorithm());
    }  
}
