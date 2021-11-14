package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WriteCsvFileHelperTest {
    private final static String CVS_FILENAME = "exampleOutput.cvs";

    @AfterAll
    public static void clearup() throws IOException {
       Files.deleteIfExists(Paths.get(CVS_FILENAME));
    }

    @Test
    public void givenCommaContainingData_whenEscapeSpecialCharacters_stringReturnedInQuotes() {
        String data = "three,two,one";
        String escapedData = WriteCsvFileHelper.escapeSpecialCharacters(data);

        String expectedData = "\"three,two,one\"";
        assertEquals(expectedData, escapedData);
    }

    @Test
    public void givenQuoteContainingData_whenEscapeSpecialCharacters_stringReturnedFormatted() {
        String data = "She said \"Hello\"";
        String escapedData = WriteCsvFileHelper.escapeSpecialCharacters(data);

        String expectedData = "\"She said \"\"Hello\"\"\"";
        assertEquals(expectedData, escapedData);
    }

    @Test
    public void givenNewlineContainingData_whenEscapeSpecialCharacters_stringReturnedInQuotes() {
        String dataNewline = "This contains\na newline";
        String dataCarriageReturn = "This contains\r\na newline and carriage return";
        String escapedDataNl = WriteCsvFileHelper.escapeSpecialCharacters(dataNewline);
        String escapedDataCr = WriteCsvFileHelper.escapeSpecialCharacters(dataCarriageReturn);

        String expectedData = "This contains a newline";
        assertEquals(expectedData, escapedDataNl);
        String expectedDataCr = "This contains a newline and carriage return";
        assertEquals(expectedDataCr, escapedDataCr);
    }

    @Test
    public void givenNonSpecialData_whenEscapeSpecialCharacters_stringReturnedUnchanged() {
        String data = "This is nothing special";
        String returnedData = WriteCsvFileHelper.escapeSpecialCharacters(data);

        assertEquals(data, returnedData);
    }

    @Test
    public void givenDataArray_whenConvertToCSV_thenOutputCreated() throws IOException {
        List<String[]> dataLines = new ArrayList<String[]>();
        dataLines.add(new String[] { "John", "Doe", "38", "Comment Data\nAnother line of comment data" });
        dataLines.add(new String[] { "Jane", "Doe, Jr.", "19", "She said \"I'm being quoted\"" });

        File csvOutputFile = new File(CVS_FILENAME);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.stream()
                .map(WriteCsvFileHelper::convertToCSV)
                .forEach(pw::println);
        } catch (FileNotFoundException e) {
            log.error("IOException " + e.getMessage());
        }

        assertTrue(csvOutputFile.exists());
    }  
}
