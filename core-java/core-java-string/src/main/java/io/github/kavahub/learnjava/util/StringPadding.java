package io.github.kavahub.learnjava.util;

import lombok.experimental.UtilityClass;

/**
 * 
 * 字符串补全
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class StringPadding {

    public String padLeftSpaces(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append(' ');
        }
        sb.append(inputString);

        return sb.toString();
    }

    public String padLeft(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(' ');
        }
        return sb.substring(inputString.length()) + inputString;
    }

    public String padLeftZeros(String inputString, int length) {
        return String
          .format("%1$" + length + "s", inputString)
          .replace(' ', '0');
    }    
}
