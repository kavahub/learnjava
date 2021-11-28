package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link Scanner} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ScannerTest {
    private static final Path TEST_READ_FILE = Paths.get("src","test","resources","test_read.in");
    private static final Path TEST_READ_MULT_FILE = Paths.get("src", "test","resources", "test_read_multiple.in");

    @Test
    public void whenReadFileWithScanner_thenCorrect() throws IOException {
        final Scanner scanner = new Scanner(TEST_READ_FILE);

        assertTrue(scanner.hasNext());
        assertEquals("Hello", scanner.next());
        assertEquals("world", scanner.next());
        assertEquals("中文", scanner.next());

        scanner.close();
    }

    @Test
    public void whenConvertInputStreamToString_thenConverted() throws IOException {
        final String expectedValue = "Hello world 中文";
        final FileInputStream inputStream = new FileInputStream(TEST_READ_FILE.toFile());
        final Scanner scanner = new Scanner(inputStream);
        scanner.useDelimiter("\\A");

        final String result = scanner.next();
        assertEquals(expectedValue, result);

        scanner.close();
    }

    @Test
    public void whenReadUsingBufferedReader_thenCorrect() throws IOException {
        final String firstLine = "Hello world";
        final String secondLine = "Hi, John";
        final BufferedReader reader = new BufferedReader(new FileReader(TEST_READ_MULT_FILE.toFile()));

        String result = reader.readLine();
        assertEquals(firstLine, result);

        result = reader.readLine();
        assertEquals(secondLine, result);

        reader.close();
    }

    @Test
    public void whenReadUsingScanner_thenCorrect() throws IOException {
        final String firstLine = "Hello world";
        final FileInputStream inputStream = new FileInputStream(TEST_READ_MULT_FILE.toFile());
        final Scanner scanner = new Scanner(inputStream);

        final String result = scanner.nextLine();
        assertEquals(firstLine, result);

        scanner.useDelimiter(", ");
        assertEquals("Hi", scanner.next());
        assertEquals("John", scanner.next());

        scanner.close();
    }

    @Test
    public void whenReadingInputFromConsole_thenCorrect() {
        final String input = "Hello";
        final InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        final Scanner scanner = new Scanner(System.in);
        final String result = scanner.next();
        assertEquals(input, result);

        System.setIn(stdin);
        scanner.close();
    }

    @Test
    public void whenValidateInputUsingScanner_thenValidated() throws IOException {
        final String input = "2000";
        final InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        final Scanner scanner = new Scanner(System.in);

        final boolean isIntInput = scanner.hasNextInt();
        assertTrue(isIntInput);

        System.setIn(stdin);
        scanner.close();
    }

    @Test
    public void whenScanString_thenCorrect() throws IOException {
        final String input = "Hello 1 F 3.5";
        final Scanner scanner = new Scanner(input);
        scanner.useLocale(Locale.US);

        assertEquals("Hello", scanner.next());
        assertEquals(1, scanner.nextInt());
        assertEquals(15, scanner.nextInt(16));
        assertEquals(3.5, scanner.nextDouble(), 0.00000001);

        scanner.close();
    }

    @Test
    public void whenFindPatternUsingScanner_thenFound() throws IOException {
        final String expectedValue = "world";
        final FileInputStream inputStream = new FileInputStream(TEST_READ_FILE.toFile());
        final Scanner scanner = new Scanner(inputStream);

        final String result = scanner.findInLine("wo..d");
        assertEquals(expectedValue, result);

        scanner.close();
    }

    @Test
    public void whenFindPatternInHorizon_thenFound() throws IOException {
        final String expectedValue = "world";
        final FileInputStream inputStream = new FileInputStream(TEST_READ_FILE.toFile());
        final Scanner scanner = new Scanner(inputStream);

        String result = scanner.findWithinHorizon("wo..d", 10);
        assertNull(result);

        // 从开始到11个字符，包括空格
        result = scanner.findWithinHorizon("wo..d", 11);
        assertEquals(expectedValue, result);

        scanner.close();
    }

    @Test
    public void whenSkipPatternUsingScanner_thenSkiped() throws IOException {
        final FileInputStream inputStream = new FileInputStream(TEST_READ_FILE.toFile());
        final Scanner scanner = new Scanner(inputStream);

        scanner.skip(".e.lo");

        assertEquals("world", scanner.next());

        scanner.close();
    }

    @Test
    public void whenChangeScannerDelimiter_thenChanged() throws IOException {
        final String expectedValue = "Hello world 中文";
        final String[] splited = expectedValue.split("o");

        final FileInputStream inputStream = new FileInputStream(TEST_READ_FILE.toFile());
        final Scanner scanner = new Scanner(inputStream);
        scanner.useDelimiter("o");

        assertEquals(splited[0], scanner.next());
        assertEquals(splited[1], scanner.next());
        assertEquals(splited[2], scanner.next());

        scanner.close();
    }

    @Test
    public void whenReadWithScannerTwoDelimiters_thenCorrect() throws IOException {
        final Scanner scanner = new Scanner(new File("src/test/resources/test_read_d.in"));
        scanner.useDelimiter(",|-");

        assertEquals("John", scanner.next());
        assertEquals("Adam", scanner.next());
        assertEquals("Tom", scanner.next());

        scanner.close();
    }    
}
