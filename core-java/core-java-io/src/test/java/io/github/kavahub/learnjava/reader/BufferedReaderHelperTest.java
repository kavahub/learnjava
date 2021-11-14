package io.github.kavahub.learnjava.reader;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class BufferedReaderHelperTest {
    private static final String INPUTE_FILE_PATH = "src/test/resources/input.txt";

    @Test
    public void givenBufferedReader_whenReadAllLines_thenReturnsContent() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(INPUTE_FILE_PATH));

        String content = BufferedReaderHelper.readAllLines(reader);

        assertThat(content).isNotEmpty();
        assertThat(content).contains("Lorem ipsum");
    }

    @Test
    public void givenBufferedReader_whenReadAllLinesWithStream_thenReturnsContent() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(INPUTE_FILE_PATH));
        String content = BufferedReaderHelper.readAllLinesWithStream(reader);

        assertThat(content).isNotEmpty();
        assertThat(content).contains("Lorem ipsum");
    }

    @Test
    public void whenReadFile_thenReturnsContent() {
        String content = BufferedReaderHelper.readFile(INPUTE_FILE_PATH);

        assertThat(content.toString()).contains("Lorem ipsum");
    }

    @Test
    public void givenBufferedReader_whenReadAllCharsOneByOne_thenReturnsContent() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(INPUTE_FILE_PATH));

        String content = BufferedReaderHelper.readAllCharsOneByOne(reader);

        assertThat(content).isNotEmpty();
        assertThat(content).contains("Lorem ipsum");
    }

    @Test
    public void givenBufferedReader_whenReadMultipleChars_thenReturnsContent() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(INPUTE_FILE_PATH));

        String content = BufferedReaderHelper.readMultipleChars(reader);

        assertThat(content).isNotEmpty();
        assertThat(content).isEqualTo("Lorem");
    }

    @Test
    public void whenReadFileTryWithResources_thenReturnsContent() {
        String content = BufferedReaderHelper.readFileTryWithResources(INPUTE_FILE_PATH);

        assertThat(content.toString()).contains("Lorem ipsum");
    }   
}
