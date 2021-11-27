package io.github.kavahub.learnjava.common.io;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;
import com.google.common.io.CharSink;
import com.google.common.io.CharSource;
import com.google.common.io.CharStreams;
import com.google.common.io.Files;
import com.google.common.io.LittleEndianDataInputStream;
import com.google.common.io.LittleEndianDataOutputStream;
import com.google.common.io.Resources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 * {@link Files} 提供了多种直接复制、写入、读取文件的方法，可以便捷的直接操作，使用较为方便
 * 
 * <p>
 * <code>Source</code> 实现了
 * <code>InputSupplier</code>，表示可读的输入流，它是一个不可变化的supplier实例。<code>ByteSource</code>
 * 和 <code>CharSource</code> ，分别用来处理字节流和字符流。
 * 
 * <p>
 * 与 <code>Source</code> 对应，<code>Sink</code> 实现了
 * <code>OutputSupplier</code>，表示可写的输出流，它也是一个不可变的supplier实例。<code>ByteSink</code>
 * 和 <code>CharSink</code> ，分别用来处理字节流和字符流。
 * 
 * <p>
 * {@link Resources} 提供操作classpath路径下所有资源的方法
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class FilesTest {
    private final static Path TEST = Paths.get("target", "test.out");
    private final static Path TEST_COPY = Paths.get("target", "test_copy.in");
    private final static Path TEST_LE = Paths.get("target", "est_le.txt");

    @BeforeEach
    public void afterEach() throws Exception {
        deleteIfExists(TEST);
        deleteIfExists(TEST_COPY);
        deleteIfExists(TEST_LE);
    }

    private void deleteIfExists(Path fileName) throws IOException {
        java.nio.file.Files.deleteIfExists(fileName);
    }

    @Test
    public void whenWriteUsingCharSink_thenWritten() throws IOException {
        final String expectedValue = "Hello world";
        final File file = TEST.toFile();
        final CharSink sink = Files.asCharSink(file, Charsets.UTF_8);

        sink.write(expectedValue);

        final String result = Files.asCharSource(file, Charsets.UTF_8).read();
        assertEquals(expectedValue, result);
    }

    @Test
    public void whenWriteMultipleLinesUsingCharSink_thenWritten() throws IOException {
        final List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        final File file = TEST.toFile();
        final CharSink sink = Files.asCharSink(file, Charsets.UTF_8);

        sink.writeLines(names, " ");

        final String result = Files.asCharSource(file, Charsets.UTF_8).read();
        final String expectedValue = Joiner.on(" ").join(names);
        assertEquals(expectedValue, result.trim());

    }

    @Test
    public void whenWriteUsingByteSink_thenWritten() throws IOException {
        final String expectedValue = "Hello world";
        final File file = TEST.toFile();
        final ByteSink sink = Files.asByteSink(file);

        sink.write(expectedValue.getBytes());

        final String result = Files.asCharSource(file, Charsets.UTF_8).read();
        assertEquals(expectedValue, result);
    }

    @Test
    public void whenReadUsingFiles_thenRead() throws IOException {
        final String expectedValue = "Hello world";
        final File file = new File("src/test/resources/test1.in");

        final String result = Files.asCharSource(file, Charsets.UTF_8).read();
        assertEquals(expectedValue, result);
    }

    @Test
    public void whenReadMultipleLinesUsingFiles_thenRead() throws IOException {
        final File file = new File("src/test/resources/test2.in");

        final List<String> result = Files.readLines(file, Charsets.UTF_8);
        assertThat(result, contains("John", "Jane", "Adam", "Tom"));
    }

    @Test
    public void whenReadUsingCharSource_thenRead() throws IOException {
        final String expectedValue = "Hello world";
        final File file = new File("src/test/resources/test1.in");

        final CharSource source = Files.asCharSource(file, Charsets.UTF_8);

        final String result = source.read();
        assertEquals(expectedValue, result);
    }

    @Test
    public void whenReadMultipleCharSources_thenRead() throws IOException {
        final String expectedValue = "Hello worldTest";
        final File file1 = new File("src/test/resources/test1.in");
        final File file2 = new File("src/test/resources/test1_1.in");

        final CharSource source1 = Files.asCharSource(file1, Charsets.UTF_8);
        final CharSource source2 = Files.asCharSource(file2, Charsets.UTF_8);
        // 合并source
        final CharSource source = CharSource.concat(source1, source2);

        final String result = source.read();

        assertEquals(expectedValue, result);
    }

    @Test
    public void whenReadUsingCharStream_thenRead() throws IOException {
        final String expectedValue = "Hello world";

        final FileReader reader = new FileReader("src/test/resources/test1.in");
        final String result = CharStreams.toString(reader);

        assertEquals(expectedValue, result);
        reader.close();
    }

    @Test
    public void whenReadUsingByteSource_thenRead() throws IOException {
        final String expectedValue = "Hello world";
        final File file = new File("src/test/resources/test1.in");

        final ByteSource source = Files.asByteSource(file);

        final byte[] result = source.read();
        assertEquals(expectedValue, new String(result));
    }

    @Test
    public void whenReadAfterOffsetUsingByteSource_thenRead() throws IOException {
        final String expectedValue = "lo world";
        final File file = new File("src/test/resources/test1.in");

        // 指定区域读
        final long offset = 3;
        final long length = 1000;
        final ByteSource source = Files.asByteSource(file).slice(offset, length);

        final byte[] result = source.read();
        assertEquals(expectedValue, new String(result));
    }

    @Test
    public void whenReadUsingByteStream_thenRead() throws IOException {
        final String expectedValue = "Hello world";

        final FileInputStream reader = new FileInputStream("src/test/resources/test1.in");
        final byte[] result = ByteStreams.toByteArray(reader);

        assertEquals(expectedValue, new String(result));
        reader.close();
    }

    @Test
    public void whenReadUsingResources_thenRead() throws IOException {
        final String expectedValue = "Hello world";

        final URL url = Resources.getResource("test1.in");
        final String result = Resources.toString(url, Charsets.UTF_8);

        assertEquals(expectedValue, result);
    }

    @Test
    public void whenCopyFileUsingFiles_thenCopied() throws IOException {
        final String expectedValue = "Hello world";

        final File file1 = new File("src/test/resources/test1.in");
        final File file2 = TEST_COPY.toFile();

        // 文件复制
        Files.copy(file1, file2);
        final String result = Files.asCharSource(file2, Charsets.UTF_8).read();

        assertEquals(expectedValue, result);
    }

    @Test
    public void whenWriteReadLittleEndian_thenCorrect() throws IOException {
        final int value = 100;

        final LittleEndianDataOutputStream writer = new LittleEndianDataOutputStream(
                new FileOutputStream(TEST_LE.toFile()));
        writer.writeInt(value);
        writer.close();

        final LittleEndianDataInputStream reader = new LittleEndianDataInputStream(
                new DataInputStream(new FileInputStream(TEST_LE.toFile())));
        final int result = reader.readInt();
        reader.close();

        assertEquals(value, result);
    }
}
