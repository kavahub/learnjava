package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileCreateTest {
    private final String FILE_TO_CREATE = "fileToCreate.txt";

    @BeforeEach
    @AfterEach
    public void cleanUpFiles() throws IOException {
        Files.deleteIfExists(Paths.get(FILE_TO_CREATE));
    }

    @Test
    public void givenUsingNio_whenCreatingFile_thenCorrect() throws IOException {
        Path newFilePath = Paths.get(FILE_TO_CREATE);
        Files.createFile(newFilePath);
    }

    @Test
    public void givenUsingFile_whenCreatingFile_thenCorrect() throws IOException {
        File newFile = new File(FILE_TO_CREATE);
        boolean success = newFile.createNewFile();
        assertTrue(success);
    }

    @Test
    void givenUsingFileOutputStream_whenCreatingFile_thenCorrect() throws IOException {
        try(FileOutputStream fileOutputStream = new FileOutputStream(FILE_TO_CREATE)){
            
        }

        assertTrue(Files.exists(Paths.get(FILE_TO_CREATE)));
    }

    @Test
    public void givenUsingGuava_whenCreatingFile_thenCorrect() throws IOException {
        com.google.common.io.Files.touch(new File(FILE_TO_CREATE));

        assertTrue(Files.exists(Paths.get(FILE_TO_CREATE)));
    }

    @Test
    public void givenUsingCommonsIo_whenCreatingFile_thenCorrect() throws IOException {
        FileUtils.touch(new File(FILE_TO_CREATE));

        assertTrue(Files.exists(Paths.get(FILE_TO_CREATE)));
    }    
}
