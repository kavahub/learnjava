package io.github.kavahub.learnjava.util;

import static io.github.kavahub.learnjava.util.CsvFileWriter.escapeSpecialCharacters;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * {@link CsvFileWriter} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
public class CsvFileWriterTest {
    private final static Path CVS_FILENAME = Paths.get("target", "CsvFileWriterTest.cvs");

    @BeforeEach
    public void clearup() throws IOException {
       Files.deleteIfExists(CVS_FILENAME);
    }

    @Test
    public void givenCommaContainingData_whenEscapeSpecialCharacters_stringReturnedInQuotes() {
        String data = "three,two,one";
        String escapedData = escapeSpecialCharacters(data);

        String expectedData = "\"three,two,one\"";
        assertEquals(expectedData, escapedData);
    }

    @Test
    public void givenQuoteContainingData_whenEscapeSpecialCharacters_stringReturnedFormatted() {
        String data = "She said \"Hello\"";
        String escapedData = escapeSpecialCharacters(data);

        String expectedData = "\"She said \"\"Hello\"\"\"";
        assertEquals(expectedData, escapedData);
    }

    @Test
    public void givenNewlineContainingData_whenEscapeSpecialCharacters_stringReturnedInQuotes() {
        String dataNewline = "This contains\na newline";
        String dataCarriageReturn = "This contains\r\na newline and carriage return";
        String escapedDataNl = escapeSpecialCharacters(dataNewline);
        String escapedDataCr = escapeSpecialCharacters(dataCarriageReturn);

        String expectedData = "This contains a newline";
        assertEquals(expectedData, escapedDataNl);
        String expectedDataCr = "This contains a newline and carriage return";
        assertEquals(expectedDataCr, escapedDataCr);
    }

    @Test
    public void givenNonSpecialData_whenEscapeSpecialCharacters_stringReturnedUnchanged() {
        String data = "This is nothing special";
        String returnedData = escapeSpecialCharacters(data);

        assertEquals(data, returnedData);
    }

    @Test
    public void givenDataArray_whenConvertToCSV_thenOutputCreated() throws IOException {
        List<String[]> dataLines = new ArrayList<String[]>();
        dataLines.add(new String[] { "John", "Doe", "38", "Comment Data\nAnother line of comment data" });
        dataLines.add(new String[] { "Jane", "Doe, Jr.", "19", "She said \"I'm being quoted\"" });

        File csvOutputFile = CVS_FILENAME.toFile();
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.stream()
                .map(CsvFileWriter::convertToCSV)
                .forEach(pw::println);
        } catch (FileNotFoundException e) {
            log.error("IOException " + e.getMessage());
        }

        assertTrue(csvOutputFile.exists());
    }  
}
