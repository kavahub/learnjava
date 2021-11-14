package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class StreamTokenizerExampleTest {
    @Test
    public void whenStreamTokenizerWithDefaultConfigurationIsCalled_ThenCorrectTokensAreReturned() throws IOException {
        Reader reader = StreamTokenizerExample.createReaderFromFile();
        List<Object> expectedTokens = Arrays.asList(3.0, "quick", "brown", "foxes", "jump", "over", "the", "lazy", "dog", '!', '#', "test1");

        List<Object> actualTokens = StreamTokenizerExample.streamTokenizerWithDefaultConfiguration(reader);

        assertArrayEquals(expectedTokens.toArray(), actualTokens.toArray());
    }

    @Test
    public void whenStreamTokenizerWithCustomConfigurationIsCalled_ThenCorrectTokensAreReturned() throws IOException {
        Reader reader = StreamTokenizerExample.createReaderFromFile();
        List<Object> expectedTokens = Arrays.asList(3.0, "quick", "brown", "foxes", "jump", "over", "the", "\"lazy\"", "dog!", '\n', '\n', '/', '/', "test2");

        List<Object> actualTokens = StreamTokenizerExample.streamTokenizerWithCustomConfiguration(reader);

        assertArrayEquals(expectedTokens.toArray(), actualTokens.toArray());
    }   
}
