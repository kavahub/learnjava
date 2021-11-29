package io.github.kavahub.learnjava.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


/**
 * 
 * 禁用词删除
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class RemoveStopwordsTest {
    final static String original = "The quick brown fox jumps over the lazy dog";
    final static String target = "quick brown fox jumps lazy dog";
    static List<String> stopwords;

    @BeforeAll
    public static void loadStopwords() throws IOException {
        stopwords = Files.readAllLines(Paths.get("src/main/resources/english_stopwords.txt"));
    }

    @Test
    public void whenRemoveStopwordsManually_thenSuccess() {
        String[] allWords = original.toLowerCase()
            .split(" ");
        StringBuilder builder = new StringBuilder();
        for (String word : allWords) {
            if (!stopwords.contains(word)) {
                builder.append(word);
                builder.append(' ');
            }
        }

        String result = builder.toString().trim();
        assertEquals(result, target);
    }

    @Test
    public void whenRemoveStopwordsUsingRemoveAll_thenSuccess() {
        ArrayList<String> allWords = Stream.of(original.toLowerCase()
            .split(" "))
            .collect(Collectors.toCollection(ArrayList<String>::new));
        allWords.removeAll(stopwords);
        String result = allWords.stream().collect(Collectors.joining(" "));
        assertEquals(result, target);
    }

    @Test
    public void whenRemoveStopwordsUsingRegex_thenSuccess() {
        String stopwordsRegex = stopwords.stream()
            .collect(Collectors.joining("|", "\\b(", ")\\b\\s?"));
        String result = original.toLowerCase().replaceAll(stopwordsRegex, "");
        assertEquals(result, target);
    }    
}
