package io.github.kavahub.learnjava;

import lombok.experimental.UtilityClass;
/**
 * 子串重复
 */
@UtilityClass
public class SubstringRepetition {
    public boolean containsOnlySubstrings(String string) {

        if (string.length() < 2) {
            return false;
        }

        StringBuilder substr = new StringBuilder();
        for (int i = 0; i < string.length() / 2; i++) {
            substr.append(string.charAt(i));

            String clearedFromSubstrings = string.replaceAll(substr.toString(), "");

            if (clearedFromSubstrings.length() == 0) {
                return true;
            }
        }

        return false;
    }

    public boolean containsOnlySubstringsEfficient(String string) {

        return ((string + string).indexOf(string, 1) != string.length());
    }    
}
