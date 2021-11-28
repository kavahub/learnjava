package io.github.kavahub.learnjava.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import lombok.experimental.UtilityClass;

/**
 * UUID生成
 * 
 * <p>
 * UID具有多个版本，每个版本的算法不同，应用范围也不同
 * 
 * <p>
 * <strong>基于时间的UUID</strong>
 * <p>
 * 基于时间的UUID通过计算当前时间戳、随机数和机器MAC地址得到。由于在算法中使用了MAC地址，这个版本的UUID可以保证在全球范围的唯一性。
 * 但与此同时，使用MAC地址会带来安全性问题。Java的UUID往往是这样实现的
 * 
 * <p>
 * <strong>DCE安全的UUID</strong>
 * <p>
 * DCE（Distributed Computing
 * Environment）安全的UUID和基于时间的UUID算法相同，但会把时间戳的前4位置换为POSIX的UID或GID。
 * 这个版本的UUID在实际中较少用到。
 * 
 * <p>
 * <strong>基于名字的UUID（MD5）</strong>
 * <p>
 * 基于名字的UUID通过计算名字和名字空间的MD5散列值得到。这个版本的UUID保证了：相同名字空间中不同名字生成的UUID的唯一性；
 * 不同名字空间中的UUID的唯一性；相同名字空间中相同名字的UUID重复生成是相同的
 * 
 * <p>
 * <strong>随机UUID</strong>
 * <p>
 * 根据随机数，或者伪随机数生成UUID。这种UUID产生重复的概率是可以计算出来的
 * 
 * <p>
 * <strong>基于名字的UUID（SHA1）</strong>
 * <p>
 * 和基于名字的UUID算法类似，只是散列值计算使用SHA1（Secure Hash Algorithm 1）算法
 * 
 * 
 * @author PinWei Wan
 * @since 1.0.0
 * 
 * @see <a href=
 *      "https://www.baeldung.com/java-generate-alphanumeric-uuid">Baeldung</a>
 * 
 */
@UtilityClass
public class UUIDGenerator {
    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    /**
     * Type 1 UUID Generation
     */
    public UUID generateType1UUID() {

        long most64SigBits = get64MostSignificantBitsForVersion1();
        long least64SigBits = get64LeastSignificantBitsForVersion1();

        return new UUID(most64SigBits, least64SigBits);
    }

    private long get64LeastSignificantBitsForVersion1() {
        Random random = new Random();
        long random63BitLong = random.nextLong() & 0x3FFFFFFFFFFFFFFFL;
        long variant3BitFlag = 0x8000000000000000L;
        return random63BitLong + variant3BitFlag;
    }

    private long get64MostSignificantBitsForVersion1() {
        LocalDateTime start = LocalDateTime.of(1582, 10, 15, 0, 0, 0);
        Duration duration = Duration.between(start, LocalDateTime.now());
        long seconds = duration.getSeconds();
        long nanos = duration.getNano();
        long timeForUuidIn100Nanos = seconds * 10000000 + nanos * 100;
        long least12SignificatBitOfTime = (timeForUuidIn100Nanos & 0x000000000000FFFFL) >> 4;
        long version = 1 << 12;
        return (timeForUuidIn100Nanos & 0xFFFFFFFFFFFF0000L) + version + least12SignificatBitOfTime;
    }

    /**
     * Type 3 UUID Generation
     *
     * @throws UnsupportedEncodingException
     */
    public UUID generateType3UUID(String namespace, String name) throws UnsupportedEncodingException {

        byte[] nameSpaceBytes = bytesFromUUID(namespace);
        byte[] nameBytes = name.getBytes("UTF-8");
        byte[] result = joinBytes(nameSpaceBytes, nameBytes);

        return UUID.nameUUIDFromBytes(result);
    }

    /**
     * Type 4 UUID Generation
     */
    public UUID generateType4UUID() {
        UUID uuid = UUID.randomUUID();
        return uuid;
    }

    /**
     * Type 5 UUID Generation
     *
     * @throws UnsupportedEncodingException
     */
    public UUID generateType5UUID(String namespace, String name) throws UnsupportedEncodingException {

        byte[] nameSpaceBytes = bytesFromUUID(namespace);
        byte[] nameBytes = name.getBytes("UTF-8");
        byte[] result = joinBytes(nameSpaceBytes, nameBytes);

        return type5UUIDFromBytes(result);
    }

    private UUID type5UUIDFromBytes(byte[] name) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException nsae) {
            throw new InternalError("SHA-1 not supported", nsae);
        }
        byte[] bytes = Arrays.copyOfRange(md.digest(name), 0, 16);
        bytes[6] &= 0x0f; /* clear version */
        bytes[6] |= 0x50; /* set to version 5 */
        bytes[8] &= 0x3f; /* clear variant */
        bytes[8] |= 0x80; /* set to IETF variant */
        return constructType5UUID(bytes);
    }

    private UUID constructType5UUID(byte[] data) {
        long msb = 0;
        long lsb = 0;
        assert data.length == 16 : "data must be 16 bytes in length";

        for (int i = 0; i < 8; i++)
            msb = (msb << 8) | (data[i] & 0xff);

        for (int i = 8; i < 16; i++)
            lsb = (lsb << 8) | (data[i] & 0xff);
        return new UUID(msb, lsb);
    }

    /**
     * Unique Keys Generation Using Message Digest and Type 4 UUID
     *
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public String generateUniqueKeysWithUUIDAndMessageDigest()
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest salt = MessageDigest.getInstance("SHA-256");
        salt.update(UUID.randomUUID().toString().getBytes("UTF-8"));
        String digest = bytesToHex(salt.digest());
        return digest;
    }

    private String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    private byte[] bytesFromUUID(String uuidHexString) {
        String normalizedUUIDHexString = uuidHexString.replace("-", "");

        assert normalizedUUIDHexString.length() == 32;

        byte[] bytes = new byte[16];
        for (int i = 0; i < 16; i++) {
            byte b = hexToByte(normalizedUUIDHexString.substring(i * 2, i * 2 + 2));
            bytes[i] = b;
        }
        return bytes;
    }

    private byte hexToByte(String hexString) {
        int firstDigit = Character.digit(hexString.charAt(0), 16);
        int secondDigit = Character.digit(hexString.charAt(1), 16);
        return (byte) ((firstDigit << 4) + secondDigit);
    }

    private byte[] joinBytes(byte[] byteArray1, byte[] byteArray2) {
        int finalLength = byteArray1.length + byteArray2.length;
        byte[] result = new byte[finalLength];

        for (int i = 0; i < byteArray1.length; i++) {
            result[i] = byteArray1[i];
        }

        for (int i = 0; i < byteArray2.length; i++) {
            result[byteArray1.length + i] = byteArray2[i];
        }

        return result;
    }

    public UUID generateType5UUID(String name) {

        try {

            byte[] bytes = name.getBytes(StandardCharsets.UTF_8);
            MessageDigest md = MessageDigest.getInstance("SHA-1");

            byte[] hash = md.digest(bytes);

            long msb = getLeastAndMostSignificantBitsVersion5(hash, 0);
            long lsb = getLeastAndMostSignificantBitsVersion5(hash, 8);
            // Set the version field
            msb &= ~(0xfL << 12);
            msb |= ((long) 5) << 12;
            // Set the variant field to 2
            lsb &= ~(0x3L << 62);
            lsb |= 2L << 62;
            return new UUID(msb, lsb);

        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }

    private long getLeastAndMostSignificantBitsVersion5(final byte[] src, final int offset) {
        long ans = 0;
        for (int i = offset + 7; i >= offset; i -= 1) {
            ans <<= 8;
            ans |= src[i] & 0xffL;
        }
        return ans;
    }
}
