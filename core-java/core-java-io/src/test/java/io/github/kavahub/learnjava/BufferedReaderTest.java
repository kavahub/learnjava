package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

/**
 * {@link BufferedReader} 示例
 */
public class BufferedReaderTest {
    private static final Path INPUT_FILE_PATH = Paths.get("src", "test", "resources", "input.txt");

    @Test
    public void givenBufferedReader_whenSkipUnderscores_thenOk() throws IOException {
        StringBuilder result = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new StringReader("1__2__3__4__5"))) {
            int value;
            while((value = reader.read()) != -1) {
                result.append((char) value);
                reader.skip(2L);
            }
        }

        assertEquals("12345", result.toString());
    }

    @Test
    public void givenBufferedReader_whenSkipsWhitespacesAtBeginning_thenOk() throws IOException {
        String result;

        try (BufferedReader reader = new BufferedReader(new StringReader("    Lorem ipsum dolor sit amet."))) {
            do {
                reader.mark(1);
            } while(Character.isWhitespace(reader.read()));

            reader.reset();
            result = reader.readLine();
        }

        assertEquals("Lorem ipsum dolor sit amet.", result);
    }

    @Test
    public void whenCreatesNewBufferedReader_thenOk() throws IOException {
        try(BufferedReader reader = Files.newBufferedReader(INPUT_FILE_PATH)) {
            assertNotNull(reader);
            assertTrue(reader.ready());
        }
    }    
}
