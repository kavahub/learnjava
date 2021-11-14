package io.github.kavahub.learnjava;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringPaddingUtil {

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
