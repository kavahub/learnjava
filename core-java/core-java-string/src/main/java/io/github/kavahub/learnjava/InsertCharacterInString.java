package io.github.kavahub.learnjava;

import lombok.experimental.UtilityClass;

@UtilityClass
public class InsertCharacterInString {

    public String insertCharSubstring(String str, char ch, int index) {
        String myString = str.substring(0, index) + ch + str.substring(index + 1);
        return myString;
    }

    public String insertCharUsingCharArray(String str, char ch, int index) {
        char[] chars = str.toCharArray();
        chars[index] = ch;
        return String.valueOf(chars);
    }


    public String insertCharStringBuilder(String str, char ch, int index) {
        StringBuilder myString = new StringBuilder(str);
        myString.setCharAt(index, ch);
        return myString.toString();
    }

    public String insertCharStringBuffer(String str, char ch, int index) {
        StringBuffer myString = new StringBuffer(str);
        myString.setCharAt(index, ch);
        return myString.toString();
    }    
}
