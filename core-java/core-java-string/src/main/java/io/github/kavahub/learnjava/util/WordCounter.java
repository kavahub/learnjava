package io.github.kavahub.learnjava.util;

import java.util.StringTokenizer;

import lombok.experimental.UtilityClass;

/**
 * 
 * 单词统计
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class WordCounter {
    static final int WORD = 0;
    static final int SEPARATOR = 1;

    public int countWordsUsingRegex(String arg) {
        if (arg == null) {
            return 0;
        }
        final String[] words = arg.split("[\\pP\\s&&[^']]+");
        return words.length;
    }

    public int countWordsUsingTokenizer(String arg) {
        if (arg == null) {
            return 0;
        }
        final StringTokenizer stringTokenizer = new StringTokenizer(arg);
        return stringTokenizer.countTokens();
    }

    public int countWordsManually(String arg) {
        if (arg == null) {
            return 0;
        }
        int flag = SEPARATOR;
        int count = 0;
        int stringLength = arg.length();
        int characterCounter = 0;

        while (characterCounter < stringLength) {
            if (isAllowedInWord(arg.charAt(characterCounter)) && flag == SEPARATOR) {
                flag = WORD;
                count++;
            } else if (!isAllowedInWord(arg.charAt(characterCounter))) {
                flag = SEPARATOR;
            }
            characterCounter++;
        }
        return count;
    }

    private boolean isAllowedInWord(char charAt) {
        return charAt == '\'' || Character.isLetter(charAt);
    }    
}
