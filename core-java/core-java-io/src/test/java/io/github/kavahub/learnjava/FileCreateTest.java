package io.github.kavahub.learnjava;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 文件创建
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class FileCreateTest {
    private final Path FILE_TO_CREATE = Paths.get("target", "fileToCreate.txt");

    @BeforeEach
    public void cleanUpFiles() throws IOException {
        Files.deleteIfExists(FILE_TO_CREATE);
    }

    @Test
    public void givenUsingNio_whenCreatingFile_thenCorrect() throws IOException {
        Files.createFile(FILE_TO_CREATE);
    }

    @Test
    public void givenUsingFile_whenCreatingFile_thenCorrect() throws IOException {
        File newFile = FILE_TO_CREATE.toFile();
        boolean success = newFile.createNewFile();
        assertTrue(success);
    }

    @Test
    void givenUsingFileOutputStream_whenCreatingFile_thenCorrect() throws IOException {
        try(FileOutputStream fileOutputStream = new FileOutputStream(FILE_TO_CREATE.toFile())){
            
        }

        assertThat(FILE_TO_CREATE).exists();
    }

    @Test
    public void givenUsingGuava_whenCreatingFile_thenCorrect() throws IOException {
        com.google.common.io.Files.touch(FILE_TO_CREATE.toFile());

        assertThat(FILE_TO_CREATE).exists();
    }

    @Test
    public void givenUsingCommonsIo_whenCreatingFile_thenCorrect() throws IOException {
        FileUtils.touch(FILE_TO_CREATE.toFile());

        assertThat(FILE_TO_CREATE).exists();
    }    
}
