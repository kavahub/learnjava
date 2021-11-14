package io.github.kavahub.learnjava.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class FileReaderHelperTest {
    private static final String FILE_PATH = "src/test/resources/HelloWorld.txt";


    @Test
    public void givenFileReader_whenReadAllCharacters_thenReturnsContent() throws IOException {
        String expectedText = "Hello, World!";
        File file = new File(FILE_PATH);
        try (FileReader fileReader = new FileReader(file)) {
            String content = FileReaderHelper.readAllCharactersOneByOne(fileReader);
            assertEquals(expectedText, content);
        }
    }

    @Test
    public void givenFileReader_whenReadMultipleCharacters_thenReturnsContent() throws IOException {
        String expectedText = "Hello";
        File file = new File(FILE_PATH);
        try (FileReader fileReader = new FileReader(file)) {
            String content = FileReaderHelper.readMultipleCharacters(fileReader, 5);
            assertEquals(expectedText, content);
        }
    }

    @Test
    public void whenReadFile_thenReturnsContent() {
        String expectedText = "Hello, World!";
        String content = FileReaderHelper.readFile(FILE_PATH);
        assertEquals(expectedText, content);
    }

    @Test
    public void whenReadFileUsingTryWithResources_thenReturnsContent() {
        String expectedText = "Hello, World!";
        String content = FileReaderHelper.readFileUsingTryWithResources(FILE_PATH);
        assertEquals(expectedText, content);
    }
    
}
