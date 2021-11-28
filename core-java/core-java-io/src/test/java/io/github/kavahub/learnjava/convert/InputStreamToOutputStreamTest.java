package io.github.kavahub.learnjava.convert;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.google.common.io.ByteStreams;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

/**
 * 
 * {@link InputStream} 与 {@link OutputStream} 的转换
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class InputStreamToOutputStreamTest {
    /**
     * Reads all bytes from an input stream and writes them to an output stream.
     * 
     * @param source - input stream to copy data from
     * @param target - output stream to copy data too
     */
    void copy(InputStream source, OutputStream target) throws IOException {
        byte[] buf = new byte[8192];
        int length;
        while ((length = source.read(buf)) > 0) {
            target.write(buf, 0, length);
        }
    }

    @Test
    public void givenUsingJavaEight_whenCopyingInputStreamToOutputStream_thenCorrect() throws IOException {
        String initialString = "Hello World!";

        try (InputStream inputStream = new ByteArrayInputStream(initialString.getBytes());
                ByteArrayOutputStream targetStream = new ByteArrayOutputStream()) {
            copy(inputStream, targetStream);

            assertEquals(initialString, new String(targetStream.toByteArray()));
        }
    }

    @Test
    public void givenUsingJavaEight_whenCopyingLongInputStreamToOutputStream_thenCorrect() throws IOException {
        String initialString = RandomStringUtils.randomAlphabetic(20480);

        try (InputStream inputStream = new ByteArrayInputStream(initialString.getBytes());
                ByteArrayOutputStream targetStream = new ByteArrayOutputStream()) {
            copy(inputStream, targetStream);

            assertEquals(initialString, new String(targetStream.toByteArray()));
        }
    }

    @Test
    public void givenUsingJavaNine_whenCopyingInputStreamToOutputStream_thenCorrect() throws IOException {
        String initialString = "Hello World!";

        try (InputStream inputStream = new ByteArrayInputStream(initialString.getBytes());
                ByteArrayOutputStream targetStream = new ByteArrayOutputStream()) {
            inputStream.transferTo(targetStream);

            assertEquals(initialString, new String(targetStream.toByteArray()));
        }
    }

    @Test
    public void givenUsingGuava_whenCopyingInputStreamToOutputStream_thenCorrect() throws IOException {
        String initialString = "Hello World!";

        try (InputStream inputStream = new ByteArrayInputStream(initialString.getBytes());
                ByteArrayOutputStream targetStream = new ByteArrayOutputStream()) {
            ByteStreams.copy(inputStream, targetStream);

            assertEquals(initialString, new String(targetStream.toByteArray()));
        }
    }

    @Test
    public void givenUsingCommonsIO_whenCopyingInputStreamToOutputStream_thenCorrect() throws IOException {
        String initialString = "Hello World!";

        try (InputStream inputStream = new ByteArrayInputStream(initialString.getBytes());
                ByteArrayOutputStream targetStream = new ByteArrayOutputStream()) {
            IOUtils.copy(inputStream, targetStream);

            assertEquals(initialString, new String(targetStream.toByteArray()));
        }
    }
}
