package io.github.kavahub.learnjava.util;

import java.util.Optional;

import lombok.experimental.UtilityClass;

/**
 * 
 * 字符串删除最后字符
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class StringRemoveLastChar {
    public String removeLastChar(String s) {
        return (s == null || s.length() == 0) ? s : (s.substring(0, s.length() - 1));
    }

    public String removeLastCharRegex(String s) {
        return (s == null) ? s : s.replaceAll(".$", "");
    }

    public String removeLastCharOptional(String s) {
        return Optional.ofNullable(s)
            .filter(str -> str.length() != 0)
            .map(str -> str.substring(0, str.length() - 1))
            .orElse(s);
    }

    public String removeLastCharRegexOptional(String s) {
        return Optional.ofNullable(s)
            .map(str -> str.replaceAll(".$", ""))
            .orElse(s);
    }   
}
