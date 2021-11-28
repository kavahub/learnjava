package io.github.kavahub.learnjava.convert;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.nio.charset.StandardCharsets;

import com.google.common.io.ByteSource;
import com.google.common.io.CharSource;
import com.google.common.io.Files;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

/**
 * 
 * 其他类型转换成 {@link InputStream} 
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class JavaXToInputStreamManualTest {
    // tests - String - InputStream

    @Test
    public final void givenUsingPlainJava_whenConvertingStringToInputStream_thenCorrect() throws IOException {
        final String initialString = "text";
        final InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());

        IOUtils.closeQuietly(targetStream);
    }

    @Test
    public final void givenUsingGuava_whenConvertingStringToInputStream_thenCorrect() throws IOException {
        final String initialString = "text";
        final InputStream targetStream = CharSource.wrap(initialString).asByteSource(StandardCharsets.UTF_8)
                .openStream();

        IOUtils.closeQuietly(targetStream);
    }

    @Test
    public final void givenUsingCommonsIO_whenConvertingStringToInputStream_thenCorrect() throws IOException {
        final String initialString = "text";
        final InputStream targetStream = IOUtils.toInputStream(initialString, StandardCharsets.UTF_8);

        IOUtils.closeQuietly(targetStream);
    }

    // byte array - InputStream

    @Test
    public final void givenUsingPlainJava_whenConvertingByteArrayToInputStream_thenCorrect() throws IOException {
        final byte[] initialArray = { 0, 1, 2 };
        final InputStream targetStream = new ByteArrayInputStream(initialArray);

        IOUtils.closeQuietly(targetStream);
    }

    @Test
    public final void givenUsingGuava_whenConvertingByteArrayToInputStream_thenCorrect() throws IOException {
        final byte[] initialArray = { 0, 1, 2 };
        final InputStream targetStream = ByteSource.wrap(initialArray).openStream();

        IOUtils.closeQuietly(targetStream);
    }

    // File - InputStream

    /**
     * FileInputStream流被称为文件字节输入流，意思指对文件数据以字节的形式进行读取操作如读取图片视频等。
     * 我们使用FileInputStream从某个文件中获得输入字节
     * 
     * @throws IOException
     */
    @Test
    public final void givenUsingPlainJava_whenConvertingFileToInputStream_thenCorrect() throws IOException {
        final File initialFile = new File("src/test/resources/sample.txt");
        final InputStream targetStream = new FileInputStream(initialFile);

        IOUtils.closeQuietly(targetStream);
    }

    /**
     * DataInputStream数据输入流允许应用程序以机器无关的方式从底层输入流中读取基本的Java类型.
     * 
     * @throws IOException
     */
    @Test
    public final void givenUsingPlainJava_whenConvertingFileToDataInputStream_thenCorrect() throws IOException {
        final File initialFile = new File("src/test/resources/sample.txt");
        final InputStream targetStream = new DataInputStream(new FileInputStream(initialFile));

        IOUtils.closeQuietly(targetStream);
    }

    /**
     * SequenceInputStream序列流可以把多个字节输入流整合成一个。它从一个有序的输入流集合开始，
     * 从第一个读取到文件的结尾，然后从第二个文件读取，依此类推，直到最后一个输入流达到文件的结尾。
     * 
     * @throws IOException
     */
    @Test
    public final void givenUsingPlainJava_whenConvertingFileToSequenceInputStream_thenCorrect() throws IOException {
        final File initialFile = new File("src/test/resources/sample.txt");
        final File anotherFile = new File("src/test/resources/anothersample.txt");
        final InputStream targetStream = new FileInputStream(initialFile);
        final InputStream anotherTargetStream = new FileInputStream(anotherFile);

        InputStream sequenceTargetStream = new SequenceInputStream(targetStream, anotherTargetStream);

        IOUtils.closeQuietly(targetStream);
        IOUtils.closeQuietly(anotherTargetStream);
        IOUtils.closeQuietly(sequenceTargetStream);
    }

    @Test
    public final void givenUsingGuava_whenConvertingFileToInputStream_thenCorrect() throws IOException {
        final File initialFile = new File("src/test/resources/sample.txt");
        final InputStream targetStream = Files.asByteSource(initialFile).openStream();

        IOUtils.closeQuietly(targetStream);
    }

    @Test
    public final void givenUsingCommonsIO_whenConvertingFileToInputStream_thenCorrect() throws IOException {
        final File initialFile = new File("src/test/resources/sample.txt");
        final InputStream targetStream = FileUtils.openInputStream(initialFile);

        IOUtils.closeQuietly(targetStream);
    }
}
