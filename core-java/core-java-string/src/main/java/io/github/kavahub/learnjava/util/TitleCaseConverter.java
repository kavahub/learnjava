package io.github.kavahub.learnjava.util;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.BreakIterator;

import org.apache.commons.text.WordUtils;

import lombok.experimental.UtilityClass;

/**
 * 
 * 字符串转换成标题型
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class TitleCaseConverter {
    private static final String WORD_SEPARATOR = " ";

    public String convertToTitleCaseIteratingChars(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        StringBuilder converted = new StringBuilder();

        boolean convertNext = true;
        for (char ch : text.toCharArray()) {
            if (Character.isSpaceChar(ch)) {
                convertNext = true;
            } else if (convertNext) {
                ch = Character.toTitleCase(ch);
                convertNext = false;
            } else {
                ch = Character.toLowerCase(ch);
            }
            converted.append(ch);
        }

        return converted.toString();
    }

    public String convertToTitleCaseSplitting(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        return Arrays
          .stream(text.split(WORD_SEPARATOR))
          .map(word -> word.isEmpty()
            ? word
            : Character.toTitleCase(word.charAt(0)) + word
              .substring(1)
              .toLowerCase())
          .collect(Collectors.joining(WORD_SEPARATOR));
    }

    public String convertToTitleCaseIcu4j(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        return UCharacter.toTitleCase(text, BreakIterator.getWordInstance());
    }

    public String convertToTileCaseWordUtilsFull(String text) {
        return WordUtils.capitalizeFully(text);
    }

    public String convertToTileCaseWordUtils(String text) {
        return WordUtils.capitalize(text);
    }   
}
