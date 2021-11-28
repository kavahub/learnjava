package io.github.kavahub.learnjava.convert;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

/**
 * 
 * {@link InputStream} 与字节数组的转换
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class InputStreamToByteArrayTest {
    @Test
    public final void givenUsingPlainJavaOnFixedSizeStream_whenConvertingAnInputStreamToAByteArray_thenCorrect() throws IOException {
        byte[] input = new byte[] { 0, 1, 2 };
        final InputStream is = new ByteArrayInputStream(input);
        final byte[] targetArray = new byte[is.available()];

        is.read(targetArray);

        assertArrayEquals(targetArray, input);
    }

    @Test
    public final void givenUsingPlainJavaOnUnknownSizeStream_whenConvertingAnInputStreamToAByteArray_thenCorrect() throws IOException {
        byte[] input = new byte[] { 0, 1, 2, 3, 4, 5, 6 };
        final InputStream is = new ByteArrayInputStream(input);
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        final byte[] data = new byte[4];

        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();
        final byte[] targetArray = buffer.toByteArray();

        assertArrayEquals(targetArray, input);
    }

    @Test
    public final void givenUsingPlainJava9OnUnknownSizeStream_whenConvertingAnInputStreamToAByteArray_thenCorrect() throws IOException {
        byte[] input = new byte[] { 0, 1, 2, 3, 4, 5, 6 };
        final InputStream is = new ByteArrayInputStream(input);
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        final byte[] data = new byte[4];

        while ((nRead = is.readNBytes(data, 0, data.length)) != 0) {
            System.out.println("here " + nRead);
            buffer.write(data, 0, nRead);
        }

        buffer.flush();
        final byte[] targetArray = buffer.toByteArray();

        assertArrayEquals(targetArray, input);
    }

    @Test
    public void givenUsingPlainJava9_whenConvertingAnInputStreamToAByteArray_thenCorrect() throws IOException {
        byte[] input = new byte[] { 0, 1, 2 };
        final InputStream is = new ByteArrayInputStream(input);

        byte[] targetArray = is.readAllBytes();
        
        assertArrayEquals(targetArray, input);
    }

    @Test
    public final void givenUsingGuava_whenConvertingAnInputStreamToAByteArray_thenCorrect() throws IOException {
        byte[] input = new byte[] { 0, 1, 2 };
        final InputStream initialStream = ByteSource.wrap(input)
            .openStream();
        final byte[] targetArray = ByteStreams.toByteArray(initialStream);

        assertArrayEquals(targetArray, input);
    }

    @Test
    public final void givenUsingCommonsIO_whenConvertingAnInputStreamToAByteArray_thenCorrect() throws IOException {
        byte[] input = new byte[] { 0, 1, 2 };
        final InputStream initialStream = new ByteArrayInputStream(input);
        final byte[] targetArray = IOUtils.toByteArray(initialStream);

        assertArrayEquals(targetArray, input);
    }   
}
