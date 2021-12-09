package io.github.kavahub.learnjava.util;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.github.kavahub.learnjava.util.DirectoryCopier.ApacheCommons;
import io.github.kavahub.learnjava.util.DirectoryCopier.CoreOld;
import io.github.kavahub.learnjava.util.DirectoryCopier.JavaNio;

/**
 * 
 * {@link DirectoryCopier} 示例
 *
 * <p>
 * 三个测试类可能同时执行, 这样就存在多线程问题：多个线程操作同一个目录。所有改为手工执行
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class DirectoryCopierManualTest {
    private final static Path sourceDirectory = Paths.get("target", "sourceDirectory1");
    private final static Path subSourceDirectory = sourceDirectory.resolve("childDirectory");
    private final static Path sourceFileName = subSourceDirectory.resolve("file.txt");

    private final static Path destinationDirectory = Paths.get("target", "destinationDirectory1");
    private final static Path subDestinationDirectory = sourceDirectory.resolve("childDirectory");
    private final static Path destinationFileName = subSourceDirectory.resolve("file.txt");

    @BeforeEach
    public void beforeEach() throws IOException {
        FileUtils.deleteDirectory(destinationDirectory.toFile());
        FileUtils.deleteDirectory(sourceDirectory.toFile());
        

        Files.createDirectories(sourceDirectory);
        Files.createDirectories(subSourceDirectory);
        Files.createFile(sourceFileName);
    }

    @Nested
    class CoreOldManualTest {

        @Test
        public void whenSourceDirectoryExists_thenDirectoryIsFullyCopied() throws IOException {
            CoreOld.copyDirectoryJavaUnder7(sourceDirectory.toFile(), destinationDirectory.toFile());

            assertTrue(Files.exists(destinationDirectory));
            assertTrue(Files.exists(subDestinationDirectory));
            assertTrue(Files.exists(destinationFileName));
        }

        @Test
        public void whenSourceDirectoryDoesNotExist_thenExceptionIsThrown() throws IOException {
            File sourceDirectory1 = new File("nonExistingDirectory");
            assertThrows(IOException.class,
                    () -> CoreOld.copyDirectoryJavaUnder7(sourceDirectory1, destinationDirectory.toFile()));
        }

    }

    @Nested
    class JavaNioManualTest {    
        @Test
        public void whenSourceDirectoryExists_thenDirectoryIsFullyCopied() throws IOException {
            JavaNio.copyDirectory(sourceDirectory.toString(), destinationDirectory.toString());

            assertTrue(Files.exists(destinationDirectory));
            assertTrue(Files.exists(subDestinationDirectory));
            assertTrue(Files.exists(destinationFileName));
        }
    
        @Test
        public void whenSourceDirectoryDoesNotExist_thenExceptionIsThrown() {
            assertThrows(IOException.class, () -> JavaNio.copyDirectory("nonExistingDirectory", destinationDirectory.toString()));
        }   
    }

    @Nested
    class ApacheCommonsManualTest {   
        @Test
        public void whenSourceDirectoryExists_thenDirectoryIsFullyCopied() throws IOException {
            ApacheCommons.copyDirectory(sourceDirectory.toString(), destinationDirectory.toString());

            assertTrue(Files.exists(destinationDirectory));
            assertTrue(Files.exists(subDestinationDirectory));
            assertTrue(Files.exists(destinationFileName));

        }
    
        @Test
        public void whenSourceDirectoryDoesNotExist_thenExceptionIsThrown() {
            assertThrows(Exception.class, () -> ApacheCommons.copyDirectory("nonExistingDirectory", destinationDirectory.toString()));
        }    
    }
}
