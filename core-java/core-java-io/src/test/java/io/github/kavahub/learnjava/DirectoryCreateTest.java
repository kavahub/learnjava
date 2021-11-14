package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DirectoryCreateTest {
    private static final File HOME = new File("tempDir");

    @AfterAll
    public static void clearUp() throws IOException {
        FileUtils.deleteDirectory(HOME);
    }

    @BeforeEach
    public void beforeEach() {
        
        File newDirectory = new File(HOME, "new_directory");
        File nestedInNewDirectory = new File(newDirectory, "nested_directory");
        File existingDirectory = new File(HOME, "existing_directory");
        File existingNestedDirectory = new File(existingDirectory, "existing_nested_directory");
        File nestedInExistingDirectory = new File(existingDirectory, "nested_directory");

        nestedInNewDirectory.delete();
        newDirectory.delete();
        nestedInExistingDirectory.delete();
        existingDirectory.mkdirs();
        existingNestedDirectory.mkdir();
    }

    @Test
    public void givenUnexistingDirectory_whenMkdir_thenTrue() {
        File newDirectory = new File(HOME, "new_directory");
        assertFalse(newDirectory.exists());

        boolean directoryCreated = newDirectory.mkdir();

        assertTrue(directoryCreated);
    }

    @Test
    public void givenExistingDirectory_whenMkdir_thenFalse() {
        File newDirectory = new File(HOME, "new_directory");
        newDirectory.mkdir();
        assertTrue(newDirectory.exists());

        boolean directoryCreated = newDirectory.mkdir();

        assertFalse(directoryCreated);
    }

    @Test
    public void givenUnexistingNestedDirectories_whenMkdir_thenFalse() {
        File newDirectory = new File(HOME, "new_directory");
        File nestedDirectory = new File(newDirectory, "nested_directory");
        assertFalse(newDirectory.exists());
        assertFalse(nestedDirectory.exists());

        boolean directoriesCreated = nestedDirectory.mkdir();

        assertFalse(directoriesCreated);
    }

    @Test
    public void givenUnexistingNestedDirectories_whenMkdirs_thenTrue() {
        File newDirectory = new File(HOME,  "new_directory");
        File nestedDirectory = new File(newDirectory, "nested_directory");
        assertFalse(newDirectory.exists());
        assertFalse(nestedDirectory.exists());

        boolean directoriesCreated = nestedDirectory.mkdirs();

        assertTrue(directoriesCreated);
    }

    @Test
    public void givenExistingParentDirectories_whenMkdirs_thenTrue() {
        File newDirectory = new File(HOME, "existing_directory");
        newDirectory.mkdir();
        File nestedDirectory = new File(newDirectory, "nested_directory");
        assertTrue(newDirectory.exists());
        assertFalse(nestedDirectory.exists());

        boolean directoriesCreated = nestedDirectory.mkdirs();

        assertTrue(directoriesCreated);
    }

    @Test
    public void givenExistingNestedDirectories_whenMkdirs_thenFalse() {
        File existingDirectory = new File(HOME, "existing_directory");
        File existingNestedDirectory = new File(existingDirectory, "existing_nested_directory");
        assertTrue(existingDirectory.exists());
        assertTrue(existingNestedDirectory.exists());

        boolean directoriesCreated = existingNestedDirectory.mkdirs();

        assertFalse(directoriesCreated);
    }  
}
