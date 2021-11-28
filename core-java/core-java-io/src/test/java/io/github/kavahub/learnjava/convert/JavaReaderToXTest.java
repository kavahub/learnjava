package io.github.kavahub.learnjava.convert;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.common.io.CharSink;
import com.google.common.io.CharSource;
import com.google.common.io.CharStreams;
import com.google.common.io.FileWriteMode;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.CharSequenceReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
 * 
 * {@link Reader} 转换成其他
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class JavaReaderToXTest {
    private final static String MIX_STRING = "Some Test 1 : 中文";
    private final static String TARGET_FILENAME = "targetfile.txt";
    // tests - sandbox

    // tests - Reader to String

    @AfterEach
    public void clearup() throws IOException {
        Files.deleteIfExists(Paths.get(TARGET_FILENAME));
    }

    @Test
    public void givenUsingPlainJava_whenConvertingReaderIntoStringV1_thenCorrect() throws IOException {
        final Reader reader = new StringReader(MIX_STRING);
        int intValueOfChar;
        String targetString = "";
        while ((intValueOfChar = reader.read()) != -1) {
            targetString += (char) intValueOfChar;
        }
        reader.close();

        assertEquals(MIX_STRING, targetString);
    }

    @Test
    public void givenUsingPlainJava_whenConvertingReaderIntoStringV2_thenCorrect() throws IOException {
        final Reader initialReader = new StringReader(MIX_STRING);
        final char[] arr = new char[8 * 1024];
        final StringBuilder buffer = new StringBuilder();
        int numCharsRead;
        while ((numCharsRead = initialReader.read(arr, 0, arr.length)) != -1) {
            buffer.append(arr, 0, numCharsRead);
        }
        initialReader.close();
        final String targetString = buffer.toString();
        assertEquals(MIX_STRING, targetString);
    }

    @Test
    public void givenUsingGuava_whenConvertingReaderIntoString_thenCorrect() throws IOException {
        final Reader initialReader = CharSource.wrap(MIX_STRING).openStream();
        final String targetString = CharStreams.toString(initialReader);
        initialReader.close();

        assertEquals(MIX_STRING, targetString);
    }

    @Test
    public void givenUsingCommonsIO_whenConvertingReaderIntoString_thenCorrect() throws IOException {
        final Reader initialReader = new StringReader(MIX_STRING);
        final String targetString = IOUtils.toString(initialReader);
        initialReader.close();

        assertEquals(MIX_STRING, targetString);
    }

    // tests - Reader WRITE TO File

    @Test
    public void givenUsingPlainJava_whenWritingReaderContentsToFile_thenCorrect() throws IOException {
        final Reader initialReader = new StringReader(MIX_STRING);

        int intValueOfChar;
        final StringBuilder buffer = new StringBuilder();
        while ((intValueOfChar = initialReader.read()) != -1) {
            buffer.append((char) intValueOfChar);
        }
        initialReader.close();

        final File targetFile = new File(TARGET_FILENAME);
        //targetFile.createNewFile();

        final Writer targetFileWriter = new FileWriter(targetFile);
        targetFileWriter.write(buffer.toString());
        targetFileWriter.close();

        assertTrue(targetFile.exists());
    }

    @Test
    public void givenUsingGuava_whenWritingReaderContentsToFile_thenCorrect() throws IOException {
        final Reader initialReader = new StringReader(MIX_STRING);

        final File targetFile = new File(TARGET_FILENAME);
        com.google.common.io.Files.touch(targetFile);
        final CharSink charSink = com.google.common.io.Files.asCharSink(targetFile, Charset.defaultCharset(), FileWriteMode.APPEND);
        charSink.writeFrom(initialReader);
        initialReader.close();

        assertTrue(targetFile.exists());
    }

    @Test
    public void givenUsingCommonsIO_whenWritingReaderContentsToFile_thenCorrect() throws IOException {
        final Reader initialReader = new CharSequenceReader(MIX_STRING);

        final File targetFile = new File(TARGET_FILENAME);
        FileUtils.touch(targetFile);
        final byte[] buffer = IOUtils.toByteArray(initialReader, StandardCharsets.UTF_8);
        FileUtils.writeByteArrayToFile(targetFile, buffer);
        initialReader.close();

        assertTrue(targetFile.exists());
    }

    // tests - Reader to byte[]

    @Test
    public void givenUsingPlainJava_whenConvertingReaderIntoByteArray_thenCorrect() throws IOException {
        final Reader initialReader = new StringReader(MIX_STRING);

        final char[] charArray = new char[8 * 1024];
        final StringBuilder builder = new StringBuilder();
        int numCharsRead;
        while ((numCharsRead = initialReader.read(charArray, 0, charArray.length)) != -1) {
            builder.append(charArray, 0, numCharsRead);
        }
        final byte[] targetArray = builder.toString().getBytes();

        initialReader.close();

        assertEquals(MIX_STRING, builder.toString());
    }

    @Test
    public void givenUsingGuava_whenConvertingReaderIntoByteArray_thenCorrect() throws IOException {
        final Reader initialReader = CharSource.wrap(MIX_STRING).openStream();

        final byte[] targetArray = CharStreams.toString(initialReader).getBytes();

        initialReader.close();

        assertEquals(MIX_STRING, new String(targetArray));
    }

    @Test
    public void givenUsingCommonsIO_whenConvertingReaderIntoByteArray_thenCorrect() throws IOException {
        final StringReader initialReader = new StringReader(MIX_STRING);

        final byte[] targetArray = IOUtils.toByteArray(initialReader, StandardCharsets.UTF_8);

        initialReader.close();

        assertEquals(MIX_STRING, new String(targetArray));
    }

    // tests - Reader to InputStream

    @Test
    public void givenUsingPlainJava_whenConvertingReaderIntoInputStream_thenCorrect() throws IOException {
        final Reader initialReader = new StringReader(MIX_STRING);

        final char[] charBuffer = new char[8 * 1024];
        final StringBuilder builder = new StringBuilder();
        int numCharsRead;
        while ((numCharsRead = initialReader.read(charBuffer, 0, charBuffer.length)) != -1) {
            builder.append(charBuffer, 0, numCharsRead);
        }
        final InputStream targetStream = new ByteArrayInputStream(builder.toString().getBytes());

        initialReader.close();
        targetStream.close();
    }

    @Test
    public void givenUsingGuava_whenConvertingReaderIntoInputStream_thenCorrect() throws IOException {
        final Reader initialReader = new StringReader(MIX_STRING);

        final InputStream targetStream = new ByteArrayInputStream(CharStreams.toString(initialReader).getBytes());

        initialReader.close();
        targetStream.close();
    }

    @Test
    public void givenUsingCommonsIO_whenConvertingReaderIntoInputStream() throws IOException {
        final Reader initialReader = new StringReader(MIX_STRING);

        final InputStream targetStream = IOUtils.toInputStream(IOUtils.toString(initialReader), StandardCharsets.UTF_8);

        initialReader.close();
        targetStream.close();
    }

    @Test
    public void givenUsingCommonsIO_whenConvertingReaderIntoInputStream_thenCorrect() throws IOException {
        String initialString = MIX_STRING;
        final Reader initialReader = new StringReader(initialString);

        final InputStream targetStream = IOUtils.toInputStream(IOUtils.toString(initialReader), StandardCharsets.UTF_8);

        final String finalString = IOUtils.toString(targetStream, StandardCharsets.UTF_8);
        assertThat(finalString, equalTo(initialString));

        initialReader.close();
        targetStream.close();
    }

    // tests - Reader to InputStream with encoding

    @Test
    public void givenUsingPlainJava_whenConvertingReaderIntoInputStreamWithCharset() throws IOException {
        final Reader initialReader = new StringReader(MIX_STRING);

        final char[] charBuffer = new char[8 * 1024];
        final StringBuilder builder = new StringBuilder();
        int numCharsRead;
        while ((numCharsRead = initialReader.read(charBuffer, 0, charBuffer.length)) != -1) {
            builder.append(charBuffer, 0, numCharsRead);
        }
        final InputStream targetStream = new ByteArrayInputStream(builder.toString().getBytes(StandardCharsets.UTF_8));

        initialReader.close();
        targetStream.close();
    }

    @Test
    public void givenUsingGuava_whenConvertingReaderIntoInputStreamWithCharset_thenCorrect() throws IOException {
        final Reader initialReader = new StringReader(MIX_STRING);

        final InputStream targetStream = new ByteArrayInputStream(CharStreams.toString(initialReader).getBytes(StandardCharsets.UTF_8));

        initialReader.close();
        targetStream.close();
    }

    @Test
    public void givenUsingCommonsIO_whenConvertingReaderIntoInputStreamWithEncoding() throws IOException {
        final Reader initialReader = new StringReader(MIX_STRING);

        final InputStream targetStream = IOUtils.toInputStream(IOUtils.toString(initialReader), StandardCharsets.UTF_8);

        initialReader.close();
        targetStream.close();
    }

    @Test
    public void givenUsingCommonsIO_whenConvertingReaderIntoInputStreamWithEncoding_thenCorrect() throws IOException {
        String initialString = MIX_STRING;
        final Reader initialReader = new StringReader(initialString);
        final InputStream targetStream = IOUtils.toInputStream(IOUtils.toString(initialReader), StandardCharsets.UTF_8);

        String finalString = IOUtils.toString(targetStream, StandardCharsets.UTF_8);
        assertThat(finalString, equalTo(initialString));

        initialReader.close();
        targetStream.close();
    }

}
