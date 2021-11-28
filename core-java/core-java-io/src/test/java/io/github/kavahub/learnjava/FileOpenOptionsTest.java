package io.github.kavahub.learnjava;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * 文件打开选项
 * 
 * @author PinWei Wan
 * @since 1.0.0
 * 
 */
public class FileOpenOptionsTest {
    private static final String HOME = "target";
    private static final String DUMMY_FILE_NAME = "sample.txt";
    private static final String EXISTING_FILE_NAME = "existingFile.txt";

    private static final String DUMMY_TEXT = "This is a sample text.";
    private static final String ANOTHER_DUMMY_TEXT = "This is a another text.";

    @BeforeAll
    public static void beforeAll() throws IOException {
        Files.deleteIfExists(Paths.get(HOME, "newfile.txt"));
        Files.deleteIfExists(Paths.get(HOME, "sparse.txt"));
        Files.deleteIfExists(Paths.get(HOME, DUMMY_FILE_NAME));
        Files.deleteIfExists(Paths.get(HOME, EXISTING_FILE_NAME));

        Path path = Paths.get(HOME, DUMMY_FILE_NAME);
        try (OutputStream out = Files.newOutputStream(path)) {
            out.write(DUMMY_TEXT.getBytes());
        }

        Files.createFile(Paths.get(HOME, EXISTING_FILE_NAME));
    }


    @Test
    public void givenExistingPath_whenCreateNewFile_thenCorrect() throws IOException {
        Path path = Paths.get(HOME, "newfile.txt");
        assertFalse(Files.exists(path));

        Files.write(path, DUMMY_TEXT.getBytes(), StandardOpenOption.CREATE);
        assertThat(path).exists();
    }

    @Test
    public void givenExistingPath_whenReadExistingFile_thenCorrect() throws IOException {
        Path path = Paths.get(HOME, DUMMY_FILE_NAME);

        try (InputStream in = Files.newInputStream(path); BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                assertThat(line, CoreMatchers.containsString(DUMMY_TEXT));
            }
        }
    }

    @Test
    public void givenExistingPath_whenWriteToExistingFile_thenCorrect() throws IOException {
        Path path = Paths.get(HOME, DUMMY_FILE_NAME);

        try (OutputStream out = Files.newOutputStream(path, StandardOpenOption.APPEND, StandardOpenOption.WRITE)) {
            out.write(ANOTHER_DUMMY_TEXT.getBytes());
        }
    }

    @Test
    public void givenExistingPath_whenCreateSparseFile_thenCorrect() throws IOException {
        Path path = Paths.get(HOME, "sparse.txt");
        Files.write(path, DUMMY_TEXT.getBytes(), StandardOpenOption.CREATE_NEW, StandardOpenOption.SPARSE);
    }

    @Test
    public void givenExistingPath_whenDeleteOnClose_thenCorrect() throws IOException {
        Path path = Paths.get(HOME, EXISTING_FILE_NAME);
        assertTrue(Files.exists(path)); // file was already created and exists

        try (OutputStream out = Files.newOutputStream(path, StandardOpenOption.APPEND, StandardOpenOption.WRITE, StandardOpenOption.DELETE_ON_CLOSE)) {
            out.write(ANOTHER_DUMMY_TEXT.getBytes());
        }

        assertFalse(Files.exists(path)); // file is deleted
    }

    @Test
    public void givenExistingPath_whenWriteAndSync_thenCorrect() throws IOException {
        Path path = Paths.get(HOME, DUMMY_FILE_NAME);
        Files.write(path, ANOTHER_DUMMY_TEXT.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.WRITE, StandardOpenOption.SYNC);
    }   
}
