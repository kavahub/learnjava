package io.github.kavahub.learnjava.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;

import lombok.experimental.UtilityClass;

/**
 * 
 * 匹配单词，字符串是否包含单词，功能实现都是全包含
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class MatchWords {
   
    public boolean containsWordsIndexOf(String inputString, String[] words) {
        boolean found = true;
        for (String word : words) {
            if (inputString.indexOf(word) == -1) {
                found = false;
                break;
            }
        }
        return found;
    }

    public boolean containsWords(String inputString, String[] items) {
        boolean found = true;
        for (String item : items) {
            if (!inputString.contains(item)) {
                found = false;
                break;
            }
        }
        return found;
    }

    public boolean containsWordsAhoCorasick(String inputString, String[] words) {
        Trie trie = Trie.builder()
                .onlyWholeWords()
                .addKeywords(words)
                .build();

        Collection<Emit> emits = trie.parseText(inputString);
        emits.forEach(System.out::println);

        boolean found = true;
        for(String word : words) {
            boolean contains = Arrays.toString(emits.toArray()).contains(word);
            if (!contains) {
                found = false;
                break;
            }
        }

        return found;
    }

    public boolean containsWordsPatternMatch(String inputString, String[] words) {

        StringBuilder regexp = new StringBuilder();
        for (String word : words) {
            regexp.append("(?=.*").append(word).append(")");
        }

        Pattern pattern = Pattern.compile(regexp.toString());

        return pattern.matcher(inputString).find();
    }

    public boolean containsWordsJava8(String inputString, String[] words) {
        List<String> inputStringList = Arrays.asList(inputString.split(" "));
        List<String> wordsList = Arrays.asList(words);

        return wordsList.stream().allMatch(inputStringList::contains);
    }

    public boolean containsWordsArray(String inputString, String[] words) {
        List<String> inputStringList = Arrays.asList(inputString.split(" "));
        List<String> wordsList = Arrays.asList(words);

        return inputStringList.containsAll(wordsList);
    }    
}
