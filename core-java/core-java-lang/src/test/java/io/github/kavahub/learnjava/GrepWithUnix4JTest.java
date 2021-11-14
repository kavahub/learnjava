package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.unix4j.Unix4j.grep;
import static org.unix4j.unix.Grep.Options;
import static org.unix4j.unix.cut.CutOption.fields;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.unix4j.line.Line;

/**
 * Unix4j是Java中Unix命令行工具的实现。 您可以在Java程序中使用从Unix知道的命令。
 * 您可以将一个命令的结果通过Unix传递给另一个命令。Unix4j为您提供unix命令的功能，
 * 例如文本处理，文件管理，以及Java等强类型和可测试语言带来的好处。
 */
public class GrepWithUnix4JTest {
    private static File fileToGrep;

    @BeforeAll
    public static void init() {
        final String separator = File.separator;
        final String filePath = String.join(separator, new String[] { "src", "test", "resources", "dictionary.in" });
        fileToGrep = new File(filePath);
    }

    @Test
    public void whenGrepWithSimpleString_thenCorrect() {
        int expectedLineCount = 5;

        // grep "NINETEEN" dictionary.in
        List<Line> lines = grep("NINETEEN", fileToGrep).toLineList();

        assertEquals(expectedLineCount, lines.size());
    }

    @Test
    public void whenInverseGrepWithSimpleString_thenCorrect() {
        int expectedLineCount = 8;

        // grep -v "NINETEEN" dictionary.in
        List<Line> lines = grep(Options.v, "NINETEEN", fileToGrep).toLineList();

        assertEquals(expectedLineCount, lines.size());
    }

    @Test
    public void whenGrepWithRegex_thenCorrect() {
        int expectedLineCount = 5;

        // grep -c ".*?NINE.*?" dictionary.in
        String patternCount = grep(Options.c, ".*?NINE.*?", fileToGrep).cut(fields, ":", 1).toStringResult();

        assertEquals(expectedLineCount, Integer.parseInt(patternCount));
    }
}
