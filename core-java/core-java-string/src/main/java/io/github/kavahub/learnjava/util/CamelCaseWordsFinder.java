package io.github.kavahub.learnjava.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.experimental.UtilityClass;

/**
 * 
 * 驼峰型字符串查找
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class CamelCaseWordsFinder {
    private static final Pattern WORD_FINDER = Pattern.compile("(([A-Z]?[a-z]+)|([A-Z]))");

    /**
     * 查找驼峰型字符串，如： ThisIsText 或者 HereIsSomeText
     * @param text the text to parse
     * @return the list of words to process
     */
    public List<String> findWordsInMixedCase(String text) {
        Matcher matcher = WORD_FINDER.matcher(text);
        List<String> words = new ArrayList<>();
        while (matcher.find()) {
            words.add(matcher.group(0));
        }
        return words;
    }  
}
