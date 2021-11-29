package io.github.kavahub.learnjava.util;

import java.util.stream.IntStream;

import lombok.experimental.UtilityClass;

/**
 * 
 * 判断回文字符串
 * 
 * <p>
 * "\\s+" 表示匹配任何空白字符，包括空格、制表符、换页符等等
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class Palindrome {
    public boolean isPalindrome(String text) {
        String clean = text.replaceAll("\\s+", "").toLowerCase();
        int length = clean.length();
        int forward = 0;
        int backward = length - 1;
        while (backward > forward) {
            char forwardChar = clean.charAt(forward++);
            char backwardChar = clean.charAt(backward--);
            if (forwardChar != backwardChar)
                return false;
        }
        return true;
    }

    public boolean isPalindromeReverseTheString(String text) {
        StringBuilder reverse = new StringBuilder();
        String clean = text.replaceAll("\\s+", "").toLowerCase();
        char[] plain = clean.toCharArray();
        for (int i = plain.length - 1; i >= 0; i--)
            reverse.append(plain[i]);
        return (reverse.toString()).equals(clean);
    }

    public boolean isPalindromeUsingStringBuilder(String text) {
        String clean = text.replaceAll("\\s+", "").toLowerCase();
        StringBuilder plain = new StringBuilder(clean);
        StringBuilder reverse = plain.reverse();
        return (reverse.toString()).equals(clean);
    }

    public boolean isPalindromeUsingStringBuffer(String text) {
        String clean = text.replaceAll("\\s+", "").toLowerCase();
        StringBuffer plain = new StringBuffer(clean);
        StringBuffer reverse = plain.reverse();
        return (reverse.toString()).equals(clean);
    }

    /**
     * 递归
     * 
     * @param text
     * @return
     */
    public boolean isPalindromeRecursive(String text) {
        String clean = text.replaceAll("\\s+", "").toLowerCase();
        return recursivePalindrome(clean, 0, clean.length() - 1);
    }

    private boolean recursivePalindrome(String text, int forward, int backward) {
        if (forward == backward)
            return true;
        if ((text.charAt(forward)) != (text.charAt(backward)))
            return false;
        if (forward < backward + 1) {
            return recursivePalindrome(text, forward + 1, backward - 1);
        }

        return true;
    }

    public boolean isPalindromeUsingIntStream(String text) {
        String temp = text.replaceAll("\\s+", "").toLowerCase();
        return IntStream.range(0, temp.length() / 2)
            .noneMatch(i -> temp.charAt(i) != temp.charAt(temp.length() - i - 1));
    }   
}
