package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChecksumHelperTest {
    byte[] arr;

    @BeforeEach
    void setUp() {
        arr =  new byte[]{0,10,21,20,35,40,120,56,72,22};
    }

    @Test
    void givenByteArray_whenChecksumCreated_checkCorrect() {

        long checksum = ChecksumHelper.getChecksumCRC32(arr);

        assertEquals(3915397664L, checksum);
    }

    @Test
    void givenTwoDifferentStrings_whenChecksumCreated_checkCollision() {

        String plumless = "plumless";
        String buckeroo = "buckeroo";

        long plumlessChecksum = ChecksumHelper.getChecksumCRC32(plumless.getBytes());
        long buckerooChecksum = ChecksumHelper.getChecksumCRC32(buckeroo.getBytes());

        assertEquals(plumlessChecksum, buckerooChecksum);
    }

    @Test
    void givenInputString_whenChecksumCreated_checkCorrect() throws IOException {

        InputStream inputStream = new ByteArrayInputStream(arr);
        long checksum = ChecksumHelper.getChecksumCRC32(inputStream, 10);

        assertEquals(3915397664L, checksum);

    }   
}
