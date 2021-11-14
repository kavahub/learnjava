package io.github.kavahub.learnjava;

import com.google.common.base.CharMatcher;

import org.apache.commons.lang3.StringUtils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RemoveLeadingAndTrailingZeroes {
    public String removeLeadingZeroesWithStringBuilder(String s) {
        StringBuilder sb = new StringBuilder(s);

        while (sb.length() > 1 && sb.charAt(0) == '0') {
            sb.deleteCharAt(0);
        }

        return sb.toString();
    }

    public String removeTrailingZeroesWithStringBuilder(String s) {
        StringBuilder sb = new StringBuilder(s);

        while (sb.length() > 1 && sb.charAt(sb.length() - 1) == '0') {
            sb.setLength(sb.length() - 1);
        }

        return sb.toString();
    }

    public String removeLeadingZeroesWithSubstring(String s) {
        int index = 0;

        for (; index < s.length() - 1; index++) {
            if (s.charAt(index) != '0') {
                break;
            }
        }

        return s.substring(index);
    }

    public String removeTrailingZeroesWithSubstring(String s) {
        int index = s.length() - 1;

        for (; index > 0; index--) {
            if (s.charAt(index) != '0') {
                break;
            }
        }

        return s.substring(0, index + 1);
    }

    public String removeLeadingZeroesWithApacheCommonsStripStart(String s) {
        String stripped = StringUtils.stripStart(s, "0");

        if (stripped.isEmpty() && !s.isEmpty()) {
            return "0";
        }

        return stripped;
    }

    public String removeTrailingZeroesWithApacheCommonsStripEnd(String s) {
        String stripped = StringUtils.stripEnd(s, "0");

        if (stripped.isEmpty() && !s.isEmpty()) {
            return "0";
        }

        return stripped;
    }

    public String removeLeadingZeroesWithGuavaTrimLeadingFrom(String s) {
        String stripped = CharMatcher.is('0')
            .trimLeadingFrom(s);

        if (stripped.isEmpty() && !s.isEmpty()) {
            return "0";
        }

        return stripped;
    }

    public String removeTrailingZeroesWithGuavaTrimTrailingFrom(String s) {
        String stripped = CharMatcher.is('0')
            .trimTrailingFrom(s);

        if (stripped.isEmpty() && !s.isEmpty()) {
            return "0";
        }

        return stripped;
    }

    public String removeLeadingZeroesWithRegex(String s) {
        return s.replaceAll("^0+(?!$)", "");
    }

    public String removeTrailingZeroesWithRegex(String s) {
        return s.replaceAll("(?!^)0+$", "");
    }    
}
