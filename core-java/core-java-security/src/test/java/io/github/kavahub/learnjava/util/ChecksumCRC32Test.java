package io.github.kavahub.learnjava.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.util.ChecksumCRC32.*;

public class ChecksumCRC32Test {
    byte[] arr;

    @BeforeEach
    void setUp() {
        arr =  new byte[]{0,10,21,20,35,40,120,56,72,22};
    }

    @Test
    void givenByteArray_whenChecksumCreated_checkCorrect() {

        long checksum = getChecksumCRC32(arr);

        assertEquals(3915397664L, checksum);
    }

    @Test
    void givenTwoDifferentStrings_whenChecksumCreated_checkCollision() {

        String plumless = "plumless";
        String buckeroo = "buckeroo";

        long plumlessChecksum = getChecksumCRC32(plumless.getBytes());
        long buckerooChecksum = getChecksumCRC32(buckeroo.getBytes());

        assertEquals(plumlessChecksum, buckerooChecksum);
    }

    @Test
    void givenInputString_whenChecksumCreated_checkCorrect() throws IOException {

        InputStream inputStream = new ByteArrayInputStream(arr);
        long checksum = getChecksumCRC32(inputStream, 10);

        assertEquals(3915397664L, checksum);

    }   
}
