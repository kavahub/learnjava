package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 可以多种方式写文件
 */
public class FileWriteManualTest {
    private static Path file = Paths.get("target", "FileWriteManualTest.txt");

    @BeforeEach
    public void setUp() throws IOException {
        Files.deleteIfExists(file);

        Files.createFile(file);
    }

    @Test
    public void whenWriteStringUsingBufferedWritter_thenCorrect() throws IOException {
        final String str = "Hello";
        final BufferedWriter writer = new BufferedWriter(new FileWriter(file.toFile()));
        writer.write(str);
        writer.close();
    }

    @Test
    public void whenAppendStringUsingBufferedWritter_thenOldContentShouldExistToo() throws IOException {
        final String str = "World";
        final BufferedWriter writer = new BufferedWriter(new FileWriter(file.toFile(), true));
        writer.append(' ');
        writer.append(str);
        writer.close();
    }

    @Test
    public void givenWritingStringToFile_whenUsingPrintWriter_thenCorrect() throws IOException {
        final FileWriter fileWriter = new FileWriter(file.toFile());
        final PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print("Some String");
        printWriter.printf("Product name is %s and its price is %d $", "iPhone", 1000);
        printWriter.close();
    }

    @Test
    public void givenWritingStringToFile_whenUsingFileOutputStream_thenCorrect() throws IOException {
        final String str = "Hello";
        final FileOutputStream outputStream = new FileOutputStream(file.toFile());
        final byte[] strToBytes = str.getBytes();
        outputStream.write(strToBytes);
        outputStream.close();
    }

    //

    @Test
    public void givenWritingToFile_whenUsingDataOutputStream_thenCorrect() throws IOException {
        final String value = "Hello";
        final FileOutputStream fos = new FileOutputStream(file.toFile());
        final DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos));
        outStream.writeUTF(value);
        outStream.close();

        String result;
        final FileInputStream fis = new FileInputStream(file.toFile());
        final DataInputStream reader = new DataInputStream(fis);
        result = reader.readUTF();
        reader.close();

        assertEquals(value, result);
    }

    @Test
    public void whenWritingToSpecificPositionInFile_thenCorrect() throws IOException {
        final int data1 = 2014;
        final int data2 = 1500;
        writeToPosition(file.toFile(), data1, 4);
        assertEquals(data1, readFromPosition(file.toFile(), 4));
        writeToPosition(file.toFile(), data2, 4);
        assertEquals(data2, readFromPosition(file.toFile(), 4));
    }

    @Test
    public void whenTryToLockFile_thenItShouldBeLocked() throws IOException {
        final RandomAccessFile stream = new RandomAccessFile(file.toFile(), "rw");
        final FileChannel channel = stream.getChannel();

        FileLock lock = null;
        try {
            lock = channel.tryLock();
        } catch (final OverlappingFileLockException e) {
            stream.close();
            channel.close();
        }
        stream.writeChars("test lock");
        lock.release();

        stream.close();
        channel.close();
    }

    @Test
    public void givenWritingToFile_whenUsingFileChannel_thenCorrect() throws IOException {
        final RandomAccessFile stream = new RandomAccessFile(file.toFile(), "rw");
        final FileChannel channel = stream.getChannel();
        final String value = "Hello";
        final byte[] strBytes = value.getBytes();
        final ByteBuffer buffer = ByteBuffer.allocate(strBytes.length);
        buffer.put(strBytes);
        buffer.flip();
        channel.write(buffer);
        stream.close();
        channel.close();

        final RandomAccessFile reader = new RandomAccessFile(file.toFile(), "r");
        assertEquals(value, reader.readLine());
        reader.close();

    }

    @Test
    public void whenWriteToTmpFile_thenCorrect() throws IOException {
        final String toWrite = "Hello";
        final File tmpFile = File.createTempFile("test", ".tmp");
        final FileWriter writer = new FileWriter(tmpFile);
        writer.write(toWrite);
        writer.close();

        final BufferedReader reader = new BufferedReader(new FileReader(tmpFile));
        assertEquals(toWrite, reader.readLine());
        reader.close();
    }

    @Test
    public void givenUsingJava7_whenWritingToFile_thenCorrect() throws IOException {
        final String str = "Hello";

        final byte[] strToBytes = str.getBytes();

        Files.write(file, strToBytes);

        final String read = Files.readAllLines(file, Charset.defaultCharset()).get(0);
        assertEquals(str, read);
    }

    // UTIL

    // use RandomAccessFile to write data at specific position in the file
    private void writeToPosition(final File file, final int data, final long position) throws IOException {
        final RandomAccessFile writer = new RandomAccessFile(file, "rw");
        writer.seek(position);
        writer.writeInt(data);
        writer.close();
    }

    // use RandomAccessFile to read data from specific position in the file
    private int readFromPosition(final File file, final long position) throws IOException {
        int result = 0;
        final RandomAccessFile reader = new RandomAccessFile(file, "r");
        reader.seek(position);
        result = reader.readInt();
        reader.close();
        return result;
    }   
}
