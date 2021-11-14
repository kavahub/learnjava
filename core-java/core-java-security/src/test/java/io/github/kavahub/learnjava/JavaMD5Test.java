package io.github.kavahub.learnjava;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

public class JavaMD5Test {
    String filename = "src/test/resources/test_md5.txt";
    String CHECKSUM = "5EB63BBBE01EEED093CB22BB8F5ACDC3";

    String HASH = "35454B055CC325EA1AF2126E27707052";
    String password = "ILoveJava";

    @Test
    public void givenPassword_whenHashing_thenVerifying() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();

        assertThat(myHash.equals(HASH)).isTrue();
    }

    @Test
    public void givenFile_generatingChecksum_thenVerifying() throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(Files.readAllBytes(Paths.get(filename)));
        byte[] digest = md.digest();
        String myChecksum = DatatypeConverter.printHexBinary(digest).toUpperCase();

        assertThat(myChecksum.equals(CHECKSUM)).isTrue();
    }

    @Test
    public void givenPassword_whenHashingUsingCommons_thenVerifying() {

        String md5Hex = DigestUtils.md5Hex(password).toUpperCase();
        assertThat(md5Hex.equals(HASH)).isTrue();
    }

    @Test
    @SuppressWarnings("deprecation")
    public void givenFile_whenChecksumUsingGuava_thenVerifying() throws IOException {
        HashCode hash = com.google.common.io.Files.hash(new File(filename), Hashing.md5());
        String myChecksum = hash.toString().toUpperCase();

        assertThat(myChecksum.equals(CHECKSUM)).isTrue();
    }   
}
