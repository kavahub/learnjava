package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 * 文件重命名
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class FileRenameTest {
    private final String FILE_TO_MOVE = "originalFileToMove.txt";
    private final String TARGET_FILE = "targetFileToMove.txt";

    @BeforeEach
    public void createFileToMove() throws IOException {
        File fileToMove = new File(FILE_TO_MOVE);
        fileToMove.createNewFile();
    }

    @AfterEach
    public void cleanUpFiles() {
        File targetFile = new File(TARGET_FILE);
        targetFile.delete();
    }

    @Test
    public void givenUsingNio_whenMovingFile_thenCorrect() throws IOException {
        Path fileToMovePath = Paths.get(FILE_TO_MOVE);
        Path targetPath = Paths.get(TARGET_FILE);
        Files.move(fileToMovePath, targetPath);

        assertFalse(Files.exists(Paths.get(FILE_TO_MOVE)));
        assertTrue(Files.exists(Paths.get(TARGET_FILE)));
    }

    @Test
    public void givenUsingFileClass_whenMovingFile_thenCorrect() throws IOException {
        File fileToMove = new File(FILE_TO_MOVE);
        boolean isMoved = fileToMove.renameTo(new File(TARGET_FILE));
        if (!isMoved) {
            throw new FileSystemException(TARGET_FILE);
        }

        assertFalse(Files.exists(Paths.get(FILE_TO_MOVE)));
        assertTrue(Files.exists(Paths.get(TARGET_FILE)));
    }

    @Test
    public void givenUsingGuava_whenMovingFile_thenCorrect() throws IOException {
        File fileToMove = new File(FILE_TO_MOVE);
        File targetFile = new File(TARGET_FILE);

        com.google.common.io.Files.move(fileToMove, targetFile);

        assertFalse(Files.exists(Paths.get(FILE_TO_MOVE)));
        assertTrue(Files.exists(Paths.get(TARGET_FILE)));
    }

    @Test
    public void givenUsingApache_whenMovingFile_thenCorrect() throws IOException {
        FileUtils.moveFile(FileUtils.getFile(FILE_TO_MOVE), FileUtils.getFile(TARGET_FILE));

        assertFalse(Files.exists(Paths.get(FILE_TO_MOVE)));
        assertTrue(Files.exists(Paths.get(TARGET_FILE)));
    }

}
