package io.github.kavahub.learnjava.util;

import static io.github.kavahub.learnjava.util.DigestAlgorithm.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.google.common.hash.Hashing;

import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.util.encoders.Hex;

import lombok.experimental.UtilityClass;

/**
 * 哈希函数（SHA256算法）
 * 
 */
@UtilityClass
public class HashAlgorithmSHA256 {
    public String hashWithJavaMessageDigest(final String originalString) throws NoSuchAlgorithmException {
        final MessageDigest digest = MessageDigest.getInstance(SHA_256);
        final byte[] encodedhash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedhash);
    }

    public String hashWithGuava(final String originalString) {
        final String sha256hex = Hashing.sha256().hashString(originalString, StandardCharsets.UTF_8).toString();
        return sha256hex;
    }

    public String hashWithApacheCommons(final String originalString) {
        final String sha256hex = DigestUtils.sha256Hex(originalString);
        return sha256hex;
    }

    public String hashWithBouncyCastle(final String originalString) throws NoSuchAlgorithmException {
        final MessageDigest digest = MessageDigest.getInstance(SHA_256);
        final byte[] hash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
        final String sha256hex = new String(Hex.encode(hash));
        return sha256hex;
    }  
}
