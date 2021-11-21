package io.github.kavahub.learnjava;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.google.common.io.ByteSink;
import com.google.common.io.MoreFiles;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 向文件中写入字节
 */
public class FileWriteByteArrayTest {
    private static final Path FILE_TO_WRITE = Paths.get("target", "FileWriteByteArrayTest.jpg");

    private byte[] dataForWriting;

    @BeforeEach
    public void setup() throws IOException {
        Files.deleteIfExists(FILE_TO_WRITE);
        dataForWriting = Files.readAllBytes(Paths.get("src", "test", "resources", "example-image.jpg"));
    }

    @Test
    public void whenUsingFileOutputStream_thenByteArrayIsWritten() throws IOException {
        final File outputFile = FILE_TO_WRITE.toFile();
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            outputStream.write(dataForWriting);
            assertThat(outputFile).hasBinaryContent(dataForWriting);
        }
    }

    @Test
    public void whenUsingNioFiles_thenByteArrayIsWritten() throws IOException {
        File outputFile = FILE_TO_WRITE.toFile();
        Files.write(outputFile.toPath(), dataForWriting);
        assertThat(outputFile).hasBinaryContent(dataForWriting);
    }

    @Test
    public void whenUsingGuavaFiles_thenByteArrayIsWritten() throws IOException {
        File outputFile = FILE_TO_WRITE.toFile();
        com.google.common.io.Files.write(dataForWriting, outputFile);
        assertThat(outputFile).hasBinaryContent(dataForWriting);
    }

    @Test
    public void whenUsingGuavaByteSink_thenByteArrayIsWritten() throws IOException {
        File outputFile = FILE_TO_WRITE.toFile();
        ByteSink byteSink = com.google.common.io.Files.asByteSink(outputFile);
        byteSink.write(dataForWriting);
        assertThat(outputFile).hasBinaryContent(dataForWriting);
    }

    @Test
    public void whenUsingGuavaByteSinkMoreFiles_thenByteArrayIsWritten() throws IOException {
        File outputFile = FILE_TO_WRITE.toFile();
        ByteSink byteSink = MoreFiles.asByteSink(outputFile.toPath(), StandardOpenOption.CREATE,
                StandardOpenOption.WRITE);
        byteSink.write(dataForWriting);
        assertThat(outputFile).hasBinaryContent(dataForWriting);
    }

    @Test
    public void whenUsingCommonsIo_thenByteArrayIsWritten() throws IOException {
        File outputFile = FILE_TO_WRITE.toFile();
        FileUtils.writeByteArrayToFile(outputFile, dataForWriting);
        assertThat(outputFile).hasBinaryContent(dataForWriting);
    }
}
