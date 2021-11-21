package io.github.kavahub.learnjava.util;

import static io.github.kavahub.learnjava.util.BufferedReaders.readAllCharsOneByOne;
import static io.github.kavahub.learnjava.util.BufferedReaders.readAllLines;
import static io.github.kavahub.learnjava.util.BufferedReaders.readAllLinesWithStream;
import static io.github.kavahub.learnjava.util.BufferedReaders.readFile;
import static io.github.kavahub.learnjava.util.BufferedReaders.readFileTryWithResources;
import static io.github.kavahub.learnjava.util.BufferedReaders.readMultipleChars;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class BufferedReadersTest {
    private static final Path INPUT_FILE_PATH = Paths.get("src", "test", "resources", "input.txt");

    @Test
    public void givenBufferedReader_whenReadAllLines_thenReturnsContent() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE_PATH.toFile()));

        String content = readAllLines(reader);

        assertThat(content).isNotEmpty();
        assertThat(content).contains("Lorem ipsum");
    }

    @Test
    public void givenBufferedReader_whenReadAllLinesWithStream_thenReturnsContent() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE_PATH.toFile()));
        String content = readAllLinesWithStream(reader);

        assertThat(content).isNotEmpty();
        assertThat(content).contains("Lorem ipsum");
    }

    @Test
    public void whenReadFile_thenReturnsContent() {
        String content = readFile(INPUT_FILE_PATH.toFile());

        assertThat(content.toString()).contains("Lorem ipsum");
    }

    @Test
    public void givenBufferedReader_whenReadAllCharsOneByOne_thenReturnsContent() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE_PATH.toFile()));

        String content = readAllCharsOneByOne(reader);

        assertThat(content).isNotEmpty();
        assertThat(content).contains("Lorem ipsum");
    }

    @Test
    public void givenBufferedReader_whenReadMultipleChars_thenReturnsContent() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE_PATH.toFile()));

        String content = readMultipleChars(reader);

        assertThat(content).isNotEmpty();
        assertThat(content).isEqualTo("Lorem");
    }

    @Test
    public void whenReadFileTryWithResources_thenReturnsContent() {
        String content = readFileTryWithResources(INPUT_FILE_PATH.toFile());

        assertThat(content.toString()).contains("Lorem ipsum");
    }   
}
