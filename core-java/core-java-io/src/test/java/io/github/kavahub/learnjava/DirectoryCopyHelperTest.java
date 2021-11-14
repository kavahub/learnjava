package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.github.kavahub.learnjava.DirectoryCopyHelper.ApacheCommons;
import io.github.kavahub.learnjava.DirectoryCopyHelper.CoreOld;
import io.github.kavahub.learnjava.DirectoryCopyHelper.JavaNio;

public class DirectoryCopyHelperTest {
    private final static String sourceDirectoryLocation = "sourceDirectory1";
    private final static String subDirectoryName = "/childDirectory";
    private final static String fileName = "/file.txt";
    private final static String destinationDirectoryLocation = "destinationDirectory1";

    @BeforeEach
    public void createDirectoryWithSubdirectoryAndFile() throws IOException {
        Files.createDirectories(Paths.get(sourceDirectoryLocation));
        Files.createDirectories(Paths.get(sourceDirectoryLocation + subDirectoryName));
        Files.createFile(Paths.get(sourceDirectoryLocation + subDirectoryName + fileName));
    }

    @AfterEach
    public void cleanUp() throws IOException {
        // Files.walk(Paths.get(sourceDirectoryLocation)).sorted(Comparator.reverseOrder()).map(Path::toFile)
        //         .forEach(File::delete);
        // if (new File(destinationDirectoryLocation).exists()) {
        //     Files.walk(Paths.get(destinationDirectoryLocation)).sorted(Comparator.reverseOrder()).map(Path::toFile)
        //             .forEach(File::delete);
        // }
        FileUtils.deleteDirectory(new File(sourceDirectoryLocation));
        FileUtils.deleteDirectory(new File(destinationDirectoryLocation));
    }

    @Nested
    class CoreOldTest {
        @Test
        public void whenSourceDirectoryExists_thenDirectoryIsFullyCopied() throws IOException {
            File sourceDirectory = new File(sourceDirectoryLocation);
            File destinationDirectory = new File(destinationDirectoryLocation);
            CoreOld.copyDirectoryJavaUnder7(sourceDirectory, destinationDirectory);

            assertTrue(new File(destinationDirectoryLocation).exists());
            assertTrue(new File(destinationDirectoryLocation + subDirectoryName).exists());
            assertTrue(new File(destinationDirectoryLocation + subDirectoryName + fileName).exists());
        }

        @Test
        public void whenSourceDirectoryDoesNotExist_thenExceptionIsThrown() throws IOException {
            File sourceDirectory = new File("nonExistingDirectory");
            File destinationDirectory = new File(destinationDirectoryLocation);
            assertThrows(IOException.class,
                    () -> CoreOld.copyDirectoryJavaUnder7(sourceDirectory, destinationDirectory));
        }

    }

    @Nested
    class JavaNioUnitTest {    
        @Test
        public void whenSourceDirectoryExists_thenDirectoryIsFullyCopied() throws IOException {
            JavaNio.copyDirectory(sourceDirectoryLocation, destinationDirectoryLocation);
    
            assertTrue(new File(destinationDirectoryLocation).exists());
            assertTrue(new File(destinationDirectoryLocation + subDirectoryName).exists());
            assertTrue(new File(destinationDirectoryLocation + subDirectoryName + fileName).exists());
        }
    
        @Test
        public void whenSourceDirectoryDoesNotExist_thenExceptionIsThrown() {
            assertThrows(IOException.class, () -> JavaNio.copyDirectory("nonExistingDirectory", destinationDirectoryLocation));
        }   
    }

    @Nested
    class ApacheCommonsUnitTest {   
        @Test
        public void whenSourceDirectoryExists_thenDirectoryIsFullyCopied() throws IOException {
            ApacheCommons.copyDirectory(sourceDirectoryLocation, destinationDirectoryLocation);
    
            assertTrue(new File(destinationDirectoryLocation).exists());
            assertTrue(new File(destinationDirectoryLocation + subDirectoryName).exists());
            assertTrue(new File(destinationDirectoryLocation + subDirectoryName + fileName).exists());
        }
    
        @Test
        public void whenSourceDirectoryDoesNotExist_thenExceptionIsThrown() {
            assertThrows(Exception.class, () -> ApacheCommons.copyDirectory("nonExistingDirectory", destinationDirectoryLocation));
        }    
    }
}
