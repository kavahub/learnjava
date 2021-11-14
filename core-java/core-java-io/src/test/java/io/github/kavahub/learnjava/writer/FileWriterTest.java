package io.github.kavahub.learnjava.writer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

public class FileWriterTest {
    private final static String FILE_TO_WRITE = "FileWriterTest.txt";

    @AfterAll
    public static void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(FILE_TO_WRITE));
    }

    @Test
    public void testWriteString() throws IOException {
        try (FileWriter fileWriter = new FileWriter(FILE_TO_WRITE)) {
            fileWriter.write("Hello Folks!");
        }
        assertEquals("Hello Folks!", new String(Files.readAllBytes(Paths.get(FILE_TO_WRITE))));
    }

    @Test
    public void testAppendString() throws IOException {
        try (FileWriter fileWriter = new FileWriter(FILE_TO_WRITE)) {
            fileWriter.write("Hello Folks!");
        }
        // using another try with resources to reopen the file in append mode
        try (FileWriter fileWriter = new FileWriter(FILE_TO_WRITE, true)) {
            fileWriter.write("Hello Folks Again!");
        }

        assertEquals("Hello Folks!" + "Hello Folks Again!", new String(Files.readAllBytes(Paths.get(FILE_TO_WRITE))));
    }

    @Test
    public void testWriteCharArray() throws IOException {
        try (FileWriter fileWriter = new FileWriter(FILE_TO_WRITE)) {
            fileWriter.write("Hello Folks!".toCharArray());
        }
        assertEquals("Hello Folks!", new String(Files.readAllBytes(Paths.get(FILE_TO_WRITE))));
    }    
}
