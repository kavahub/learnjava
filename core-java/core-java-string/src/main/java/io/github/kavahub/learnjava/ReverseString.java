package io.github.kavahub.learnjava;

import org.apache.commons.lang3.StringUtils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ReverseString {
    /**
     * I am student -> tneduts ma I
     * 
     * @param input
     * @return
     */
    public String reverse(String input) {
        if (input == null) {
            return null;
        }

        String output = "";

        for (int i = input.length() - 1; i >= 0; i--) {
            output = output + input.charAt(i);
        }

        return output;
    }

    /**
     * 功能与reverse函数相同
     * @param input
     * @return
     */
    public String reverseUsingStringBuilder(String input) {
        if (input == null) {
            return null;
        }

        StringBuilder output = new StringBuilder(input).reverse();

        return output.toString();
    }

    public String reverseUsingApacheCommons(String input) {
        return StringUtils.reverse(input);
    }

    /**
     * I am student -> student am I
     * 
     * @param sentence
     * @return
     */
    public static String reverseTheOrderOfWords(String sentence) {
        if (sentence == null) {
            return null;
        }

        StringBuilder output = new StringBuilder();
        String[] words = sentence.split(" ");

        for (int i = words.length - 1; i >= 0; i--) {
            output.append(words[i]);
            output.append(" ");
        }

        return output.toString()
            .trim();
    }

    /**
     * 功能与reverseTheOrderOfWords函数相同
     * @param sentence
     * @return
     */
    public String reverseTheOrderOfWordsUsingApacheCommons(String sentence) {
        return StringUtils.reverseDelimited(sentence, ' ');
    }
    
}
