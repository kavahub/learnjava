package io.github.kavahub.learnjava.util;

import static io.github.kavahub.learnjava.util.FileReaders.readAllCharactersOneByOne;
import static io.github.kavahub.learnjava.util.FileReaders.readFile;
import static io.github.kavahub.learnjava.util.FileReaders.readFileUsingTryWithResources;
import static io.github.kavahub.learnjava.util.FileReaders.readMultipleCharacters;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link FileReaders} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class FileReadersTest {
    private static final Path FILE_PATH = Paths.get("src", "test", "resources", "HelloWorld.txt");

    @Test
    public void givenFileReader_whenReadAllCharacters_thenReturnsContent() throws IOException {
        String expectedText = "Hello, World!";
        try (FileReader fileReader = new FileReader(FILE_PATH.toFile())) {
            String content = readAllCharactersOneByOne(fileReader);
            assertEquals(expectedText, content);
        }
    }

    @Test
    public void givenFileReader_whenReadMultipleCharacters_thenReturnsContent() throws IOException {
        String expectedText = "Hello";
        try (FileReader fileReader = new FileReader(FILE_PATH.toFile())) {
            String content = readMultipleCharacters(fileReader, 5);
            assertEquals(expectedText, content);
        }
    }

    @Test
    public void whenReadFile_thenReturnsContent() {
        String expectedText = "Hello, World!";
        String content = readFile(FILE_PATH.toFile());
        assertEquals(expectedText, content);
    }

    @Test
    public void whenReadFileUsingTryWithResources_thenReturnsContent() {
        String expectedText = "Hello, World!";
        String content = readFileUsingTryWithResources(FILE_PATH.toFile());
        assertEquals(expectedText, content);
    }
    
}
