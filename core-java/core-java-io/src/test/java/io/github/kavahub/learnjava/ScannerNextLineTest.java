package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

public class ScannerNextLineTest {
    @Test
    public void whenReadingLines_thenCorrect() {
        String input = "Scanner\nTest\n";
        try (Scanner scanner = new Scanner(input)) {
            assertEquals("Scanner", scanner.nextLine());
            assertEquals("Test", scanner.nextLine());
        }
    }

    @Test
    public void whenReadingPartialLines_thenCorrect() {
        String input = "Scanner\n";
        try (Scanner scanner = new Scanner(input)) {
            scanner.useDelimiter("");
            scanner.next();
            assertEquals("canner", scanner.nextLine());
        }
    }

    @Test
    public void givenNoNewLine_whenReadingNextLine_thenThrowNoSuchElementException() {
        try (Scanner scanner = new Scanner("")) {
            assertThrows(NoSuchElementException.class, () -> scanner.nextLine());
        }
    }

    @Test
    public void givenScannerIsClosed_whenReadingNextLine_thenThrowIllegalStateException() {
        Scanner scanner = new Scanner("");
        scanner.close();
        assertThrows(IllegalStateException.class, () -> scanner.nextLine());
    }    
}
