package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@WireMockTest
public class IOvsNIOClientLiveTest {
    private static final String REQUESTED_RESOURCE = "/test.json";

    @BeforeEach
    public void setup() {
        stubFor(get(REQUESTED_RESOURCE).willReturn(ok()
          .withBody("{ \"response\" : \"It worked!\" }\r\n\r\n")));
    }

    // java io

    @Test
    public void givenJavaIOSocket_whenReadingAndWritingWithStreams_thenSuccess(WireMockRuntimeInfo wmRuntimeInfo) throws IOException {
        // given an IO socket and somewhere to store our result
        Socket socket = new Socket("localhost", wmRuntimeInfo.getHttpPort());
        StringBuilder ourStore = new StringBuilder();

        // when we write and read (using try-with-resources so our resources are auto-closed)
        try (InputStream serverInput = socket.getInputStream();
          BufferedReader reader = new BufferedReader(new InputStreamReader(serverInput));
          OutputStream clientOutput = socket.getOutputStream();
          PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientOutput))) {
            writer.print("GET " + REQUESTED_RESOURCE + " HTTP/1.0\r\n\r\n");
            writer.flush(); // important - without this the request is never sent, and the test will hang on readLine()

            for (String line; (line = reader.readLine()) != null; ) {
                ourStore.append(line);
                ourStore.append(System.lineSeparator());
            }
        }

        // then we read and saved our data
        assertTrue(ourStore
          .toString()
          .contains("It worked!"));

        socket.close();
    }

    // java nio

    @Test
    public void givenJavaNIOSocketChannel_whenReadingAndWritingWithBuffers_thenSuccess(WireMockRuntimeInfo wmRuntimeInfo) throws IOException {
        // given a NIO SocketChannel and a charset
        InetSocketAddress address = new InetSocketAddress("localhost", wmRuntimeInfo.getHttpPort());
        SocketChannel socketChannel = SocketChannel.open(address);
        Charset charset = StandardCharsets.UTF_8;

        // when we write and read using buffers
        socketChannel.write(charset.encode(CharBuffer.wrap("GET " + REQUESTED_RESOURCE + " HTTP/1.0\r\n\r\n")));

        ByteBuffer byteBuffer = ByteBuffer.allocate(8192); // or allocateDirect if we need direct memory access
        CharBuffer charBuffer = CharBuffer.allocate(8192);
        CharsetDecoder charsetDecoder = charset.newDecoder();
        StringBuilder ourStore = new StringBuilder();
        while (socketChannel.read(byteBuffer) != -1 || byteBuffer.position() > 0) {
            byteBuffer.flip();
            storeBufferContents(byteBuffer, charBuffer, charsetDecoder, ourStore);
            byteBuffer.compact();
        }
        socketChannel.close();

        // then we read and saved our data
        assertTrue(ourStore
          .toString()
          .contains("It worked!"));
    }

    @Test
    public void givenJavaNIOSocketChannel_whenReadingAndWritingWithSmallBuffers_thenSuccess(WireMockRuntimeInfo wmRuntimeInfo) throws IOException {
        // given a NIO SocketChannel and a charset
        InetSocketAddress address = new InetSocketAddress("localhost", wmRuntimeInfo.getHttpPort());
        SocketChannel socketChannel = SocketChannel.open(address);
        Charset charset = StandardCharsets.UTF_8;

        // when we write and read using buffers that are too small for our message
        socketChannel.write(charset.encode(CharBuffer.wrap("GET " + REQUESTED_RESOURCE + " HTTP/1.0\r\n\r\n")));

        ByteBuffer byteBuffer = ByteBuffer.allocate(8); // or allocateDirect if we need direct memory access
        CharBuffer charBuffer = CharBuffer.allocate(8);
        CharsetDecoder charsetDecoder = charset.newDecoder();
        StringBuilder ourStore = new StringBuilder();
        while (socketChannel.read(byteBuffer) != -1 || byteBuffer.position() > 0) {
            byteBuffer.flip();
            storeBufferContents(byteBuffer, charBuffer, charsetDecoder, ourStore);
            byteBuffer.compact();
        }
        socketChannel.close();

        // then we read and saved our data
        assertTrue(ourStore
          .toString()
          .contains("It worked!"));
    }

    void storeBufferContents(ByteBuffer byteBuffer, CharBuffer charBuffer, CharsetDecoder charsetDecoder, StringBuilder ourStore) {
        charsetDecoder.decode(byteBuffer, charBuffer, true);
        charBuffer.flip();
        ourStore.append(charBuffer);
        charBuffer.clear();
    }
}
