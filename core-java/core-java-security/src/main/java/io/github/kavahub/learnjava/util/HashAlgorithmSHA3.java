package io.github.kavahub.learnjava.util;

import static io.github.kavahub.learnjava.util.DigestAlgorithm.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import lombok.experimental.UtilityClass;

/**
 * 
 * 哈希函数（PBKDF2算法）
 * 
 * <p>
 * SHA-3第三代安全散列算法(Secure Hash Algorithm 3)， 之前名为Keccak（念作/ˈkɛtʃæk/或/kɛtʃɑːk/)）算法
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class HashAlgorithmSHA3 {
    /* works with JDK9+ only */
    public String hashWithJavaMessageDigestJDK9(final String originalString) throws NoSuchAlgorithmException {
        final MessageDigest digest = MessageDigest.getInstance(SHA3_256);
        final byte[] hashbytes = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hashbytes);
    }

    public String hashWithJavaMessageDigest(final String originalString) throws NoSuchAlgorithmException {
        Security.addProvider(new BouncyCastleProvider());
        final MessageDigest digest = MessageDigest.getInstance(SHA3_256);
        final byte[] hashbytes = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hashbytes);
    }

    /* works with JDK9+ only */
    public String hashWithApacheCommonsJDK9(final String originalString) {
        return new DigestUtils(SHA3_256).digestAsHex(originalString);
    }

    public String hashWithBouncyCastle(final String originalString) {
        SHA3.Digest256 digest256 = new SHA3.Digest256();
        byte[] hashbytes = digest256.digest(originalString.getBytes(StandardCharsets.UTF_8));
        return new String(Hex.encode(hashbytes));
    }
}
