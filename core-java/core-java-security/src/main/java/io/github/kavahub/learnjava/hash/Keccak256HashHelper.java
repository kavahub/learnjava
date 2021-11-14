package io.github.kavahub.learnjava.hash;

import static io.github.kavahub.learnjava.hash.DigestAlgorithm.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import lombok.experimental.UtilityClass;

/**
 * 哈希函数（Keccak算法）
 * 
 * BouncyCastle就是一个提供了很多哈希算法和加密算法的第三方库。它提供了Java标准库没有的一些算法，例如，RipeMD160哈希算法。
 */
@UtilityClass
public class Keccak256HashHelper {

    
    public String hashWithJavaMessageDigest(final String originalString) throws NoSuchAlgorithmException {
        Security.addProvider(new BouncyCastleProvider());
        final MessageDigest digest = MessageDigest.getInstance(KECCAK_256);
        final byte[] encodedhash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedhash);
    }

    public String hashWithBouncyCastle(final String originalString) {
        Keccak.Digest256 digest256 = new Keccak.Digest256();
        byte[] hashbytes = digest256.digest(originalString.getBytes(StandardCharsets.UTF_8));
        return new String(Hex.encode(hashbytes));
    }

}
