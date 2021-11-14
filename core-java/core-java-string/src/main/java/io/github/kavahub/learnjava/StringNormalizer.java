package io.github.kavahub.learnjava;

import java.text.Normalizer;
import java.util.StringJoiner;

import org.apache.commons.lang3.StringUtils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringNormalizer {
    /**
     * 删除字符串中的音标
     * @param input
     * @return
     */
    String removeAccentsWithApacheCommons(String input) {
        return StringUtils.stripAccents(input);
    }

    String removeAccents(String input) {
        return normalize(input).replaceAll("\\p{M}", "");
    }

    String unicodeValueOfNormalizedString(String input) {
        return toUnicode(normalize(input));
    }

    /**
     * 规范化
     * @param input
     * @return
     */
    private String normalize(String input) {
        return input == null ? null : Normalizer.normalize(input, Normalizer.Form.NFKD);
    }

    private String toUnicode(String input) {
        if (input.length() == 1) {
            return toUnicode(input.charAt(0));
        } else {
            StringJoiner stringJoiner = new StringJoiner(" ");
            for (char c : input.toCharArray()) {
                stringJoiner.add(toUnicode(c));
            }
            return stringJoiner.toString();
        }
    }

    private static String toUnicode(char input) {

        String hex = Integer.toHexString(input);
        StringBuilder sb = new StringBuilder(hex);

        while (sb.length() < 4) {
            sb.insert(0, "0");
        }
        sb.insert(0, "\\u");
        return sb.toString();
    }
}
