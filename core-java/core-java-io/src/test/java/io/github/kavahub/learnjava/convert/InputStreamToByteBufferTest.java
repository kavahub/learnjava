package io.github.kavahub.learnjava.convert;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

public class InputStreamToByteBufferTest {
    @Test
    public void givenUsingCoreClasses_whenByteArrayInputStreamToAByteBuffer_thenLengthMustMatch() throws IOException {
        byte[] input = new byte[] { 0, 1, 2 };
        InputStream initialStream = new ByteArrayInputStream(input);
        ByteBuffer byteBuffer = ByteBuffer.allocate(3);
        while (initialStream.available() > 0) {
            byteBuffer.put((byte) initialStream.read());
        }

        assertEquals(byteBuffer.position(), input.length);
    }

    @Test
    public void givenUsingGuava__whenByteArrayInputStreamToAByteBuffer_thenLengthMustMatch() throws IOException {
        InputStream initialStream = ByteSource
          .wrap(new byte[] { 0, 1, 2 })
          .openStream();
        byte[] targetArray = ByteStreams.toByteArray(initialStream);
        ByteBuffer bufferByte = ByteBuffer.wrap(targetArray);
        while (bufferByte.hasRemaining()) {
            bufferByte.get();
        }

        assertEquals(bufferByte.position(), targetArray.length);
    }

    @Test
    public void givenUsingCommonsIo_whenByteArrayInputStreamToAByteBuffer_thenLengthMustMatch() throws IOException {
        byte[] input = new byte[] { 0, 1, 2 };
        InputStream initialStream = new ByteArrayInputStream(input);
        ByteBuffer byteBuffer = ByteBuffer.allocate(3);
        ReadableByteChannel channel = Channels.newChannel(initialStream);
        IOUtils.readFully(channel, byteBuffer);

        assertEquals(byteBuffer.position(), input.length);
    }  
}
