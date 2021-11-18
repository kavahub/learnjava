package io.github.kavahub.learnjava.util;

import lombok.experimental.UtilityClass;

/**
 * 在字符串中插入子串
 * 
 */
@UtilityClass
public class InsertCharacterInString {

    /**
     * 使用 {@link String#substring(int)} 方法
     * 
     * @param str
     * @param ch
     * @param index
     * @return
     */
    public String insertCharSubstring(String str, char ch, int index) {
        String myString = str.substring(0, index) + ch + str.substring(index + 1);
        return myString;
    }

    /**
     * 使用 {@link String#toCharArray()} 方法
     * 
     * @param str
     * @param ch
     * @param index
     * @return
     */
    public String insertCharUsingCharArray(String str, char ch, int index) {
        char[] chars = str.toCharArray();
        chars[index] = ch;
        return String.valueOf(chars);
    }

    /**
     * 使用 {@link StringBuilder#setCharAt(int, char)} 方法
     * 
     * @param str
     * @param ch
     * @param index
     * @return
     */
    public String insertCharStringBuilder(String str, char ch, int index) {
        StringBuilder myString = new StringBuilder(str);
        myString.setCharAt(index, ch);
        return myString.toString();
    }

    /**
     * 使用 {@link StringBuffer#setCharAt(int, char)} 方法
     * 
     * @param str
     * @param ch
     * @param index
     * @return
     */
    public String insertCharStringBuffer(String str, char ch, int index) {
        StringBuffer myString = new StringBuffer(str);
        myString.setCharAt(index, ch);
        return myString.toString();
    }
}
