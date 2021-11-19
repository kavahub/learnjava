package io.github.kavahub.learnjava.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DigestAlgorithm {
    public static final String SHA3_256 = "SHA3-256";
    public static final String SHA_256 = "SHA-256";
    public static final String KECCAK_256 = "Keccak-256";

    public String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte h : hash) {
            String hex = Integer.toHexString(0xff & h);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
