package io.github.kavahub.learnjava;

import java.io.IOException;
import java.util.Scanner;

import lombok.extern.slf4j.Slf4j;

/**
 * {@link Scanner} 示例，读取字符串（包含换行符）
 */
@Slf4j
public class ScannerExample {
    private static final String LINE = "----------------------------";
    private static final String END_LINE = "--------OUTPUT--END---------\n";


    private static final String INPUT = new StringBuilder()
        .append("magic\tproject\n")
        .append("     database: oracle\n")
        .append("dependencies:\n")
        .append("spring:foo:bar\n")
        .append("\n").toString();

    private static void hasNextBasic() {
        printHeader("hasNext() Basic");
        Scanner scanner = new Scanner(INPUT);
        while (scanner.hasNext()) {
            log.info(scanner.next());
        }
        log.info(END_LINE);
        scanner.close();
    }

    private static void hasNextWithDelimiter() {
        printHeader("hasNext() with delimiter");
        Scanner scanner = new Scanner(INPUT);
        while (scanner.hasNext()) {
            String token = scanner.next();
            if ("dependencies:".equals(token)) {
                scanner.useDelimiter(":");
            }
            log.info(token);
        }
        log.info(END_LINE);
        scanner.close();
    }

    private static void hasNextWithDelimiterFixed() {
        printHeader("hasNext() with delimiter FIX");
        Scanner scanner = new Scanner(INPUT);
        while (scanner.hasNext()) {
            String token = scanner.next();
            if ("dependencies:".equals(token)) {
                scanner.useDelimiter(":|\\s+");
            }
            log.info(token);
        }
        log.info(END_LINE);
        scanner.close();
    }

    private static void addLineNumber() {
        printHeader("add line number by hasNextLine() ");
        Scanner scanner = new Scanner(INPUT);
        int i = 0;
        while (scanner.hasNextLine()) {
            log.info(String.format("%d|%s", ++i, scanner.nextLine()));
        }
        log.info(END_LINE);
        scanner.close();
    }

    private static void printHeader(String title) {
        log.info(LINE);
        log.info(title);
        log.info(LINE);
    }

    public static void main(String[] args) throws IOException {
        hasNextBasic();
        hasNextWithDelimiter();
        hasNextWithDelimiterFixed();
        addLineNumber();
    }

}
