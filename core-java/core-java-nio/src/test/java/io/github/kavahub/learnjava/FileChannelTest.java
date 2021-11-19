package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

public class FileChannelTest {
    private final static String TEST_WRITE_USING_FILECHANNEL = "test_write_using_filechannel.txt";
    private final static String TEST_TRUNCATE = "test_truncate.txt";

    @AfterAll
    public static void clear() throws IOException {
        Path path = Paths.get(TEST_WRITE_USING_FILECHANNEL);
        Files.deleteIfExists(path);

        path = Paths.get(TEST_TRUNCATE);
        Files.deleteIfExists(path);
    }

    @Test
    public void givenFile_whenReadWithFileChannelUsingRandomAccessFile_thenCorrect() throws IOException {
        // RandomAccessFile读取文件
        try (RandomAccessFile reader = new RandomAccessFile("src/test/resources/test_read.in", "r");
                // 获取读通道
                FileChannel channel = reader.getChannel();
                ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            // 缓冲区大小
            int bufferSize = 1024;
            if (bufferSize > channel.size()) {
                bufferSize = (int) channel.size();
            }
            // 创建缓冲区
            ByteBuffer buff = ByteBuffer.allocate(bufferSize);

            // 循环读取文件
            int byteRead;
            while ((byteRead = channel.read(buff)) > 0) {
                // 输出
                out.write(buff.array(), 0, byteRead);
                buff.clear();
            }

            // 转换成字符串
            String fileContent = new String(out.toByteArray(), StandardCharsets.UTF_8);

            assertEquals("Hello world", fileContent);
        }
    }

    @Test
    public void givenFile_whenReadWithFileChannelUsingFileInputStream_thenCorrect() throws IOException {
        // FileInputStream读取文件
        try (FileInputStream fin = new FileInputStream("src/test/resources/test_read.in");
                FileChannel channel = fin.getChannel();
                ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            int bufferSize = 1024;
            if (bufferSize > channel.size()) {
                bufferSize = (int) channel.size();
            }
            ByteBuffer buff = ByteBuffer.allocate(bufferSize);

            while (channel.read(buff) > 0) {
                out.write(buff.array(), 0, buff.position());
                buff.clear();
            }
            String fileContent = new String(out.toByteArray(), StandardCharsets.UTF_8);

            assertEquals("Hello world", fileContent);
        }
    }

    @Test
    public void givenFile_whenReadAFileSectionIntoMemoryWithByteArrayOutputStream_thenCorrect() throws IOException {

        try (RandomAccessFile reader = new RandomAccessFile("src/test/resources/test_read.in", "r");
                FileChannel channel = reader.getChannel();
                ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            // MappedByteBuffer操作大文件的方式，其读写性能极高
            MappedByteBuffer buff = channel.map(FileChannel.MapMode.READ_ONLY, 6, 5);

            if (buff.hasRemaining()) {
                byte[] data = new byte[buff.remaining()];
                buff.get(data);
                assertEquals("world", new String(data, StandardCharsets.UTF_8));
            }
        }
    }

    @Test
    public void whenWriteWithFileChannelUsingRandomAccessFile_thenCorrect() throws IOException {

        try (RandomAccessFile writer = new RandomAccessFile(TEST_WRITE_USING_FILECHANNEL, "rw");
                FileChannel channel = writer.getChannel()) {
            ByteBuffer buff = ByteBuffer.wrap("Hello world".getBytes(StandardCharsets.UTF_8));

            channel.write(buff);

            // now we verify whether the file was written correctly
            RandomAccessFile reader = new RandomAccessFile(TEST_WRITE_USING_FILECHANNEL, "r");
            assertEquals("Hello world", reader.readLine());
            reader.close();
        }
    }

    @Test
    public void givenFile_whenWriteAFileUsingLockAFileSectionWithFileChannel_thenCorrect() throws IOException {
        try (RandomAccessFile reader = new RandomAccessFile("src/test/resources/test_read.in", "rw");
                FileChannel channel = reader.getChannel();
                FileLock fileLock = channel.tryLock(6, 5, Boolean.FALSE);) {

            assertNotNull(fileLock);
        }
    }

    @Test
    public void givenFile_whenReadWithFileChannelGetPosition_thenCorrect() throws IOException {

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
                RandomAccessFile reader = new RandomAccessFile("src/test/resources/test_read.in", "r");
                FileChannel channel = reader.getChannel()) {

            int bufferSize = 1024;
            if (bufferSize > channel.size()) {
                bufferSize = (int) channel.size();
            }
            ByteBuffer buff = ByteBuffer.allocate(bufferSize);

            while (channel.read(buff) > 0) {
                out.write(buff.array(), 0, buff.position());
                buff.clear();
            }

            // the original file is 11 bytes long, so that's where the position pointer
            // should be
            assertEquals(11, channel.position());

            channel.position(4);
            assertEquals(4, channel.position());
        }
    }

    @Test
    public void whenGetFileSize_thenCorrect() throws IOException {

        try (RandomAccessFile reader = new RandomAccessFile("src/test/resources/test_read.in", "r");
                FileChannel channel = reader.getChannel()) {

            // the original file is 11 bytes long, so that's where the position pointer
            // should be
            assertEquals(11, channel.size());
            assertEquals(0, channel.position());
        }
    }

    @Test
    public void whenTruncateFile_thenCorrect() throws IOException {
        String input = "this is a test input";

        FileOutputStream fout = new FileOutputStream(TEST_TRUNCATE);
        FileChannel channel = fout.getChannel();

        ByteBuffer buff = ByteBuffer.wrap(input.getBytes());
        channel.write(buff);
        buff.flip();

        channel = channel.truncate(5);
        assertEquals(5, channel.size());

        fout.close();
        channel.close();
    }
}
