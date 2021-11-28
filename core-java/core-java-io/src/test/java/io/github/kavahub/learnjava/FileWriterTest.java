package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * {@link FileWriter} 示例
 * 
 * @author PinWei Wan
 * @since 1.0.0
 * 
 */
public class FileWriterTest {
    private final static Path FILE_TO_WRITE = Paths.get("target", "FileWriterTest.txt");

    @BeforeEach
    public void before() throws IOException {
        Files.deleteIfExists(FILE_TO_WRITE);
    }

    @Test
    public void testWriteString() throws IOException {
        try (FileWriter fileWriter = new FileWriter(FILE_TO_WRITE.toFile())) {
            fileWriter.write("Hello Folks!");
        }
        assertEquals("Hello Folks!", new String(Files.readAllBytes(FILE_TO_WRITE)));
    }

    @Test
    public void testAppendString() throws IOException {
        try (FileWriter fileWriter = new FileWriter(FILE_TO_WRITE.toFile())) {
            fileWriter.write("Hello Folks!");
        }
        // using another try with resources to reopen the file in append mode
        try (FileWriter fileWriter = new FileWriter(FILE_TO_WRITE.toFile(), true)) {
            fileWriter.write("Hello Folks Again!");
        }

        assertEquals("Hello Folks!" + "Hello Folks Again!", new String(Files.readAllBytes(FILE_TO_WRITE)));
    }

    @Test
    public void testWriteCharArray() throws IOException {
        try (FileWriter fileWriter = new FileWriter(FILE_TO_WRITE.toFile())) {
            fileWriter.write("Hello Folks!".toCharArray());
        }
        assertEquals("Hello Folks!", new String(Files.readAllBytes(FILE_TO_WRITE)));
    }    
}
