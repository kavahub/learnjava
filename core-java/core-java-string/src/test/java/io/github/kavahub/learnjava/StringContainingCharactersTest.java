package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

public class StringContainingCharactersTest {
    private static final Pattern[] inputRegexes = new Pattern[4];

     // isMatchingRegex函数与checkString函数与regex正则表达式功能相同

    private static final String regex = "^(?=.*?\\p{Lu})(?=.*?\\p{Ll})(?=.*?\\d)(?=.*?[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?]).*$";


    static {
        // 大写字母
        inputRegexes[0] = Pattern.compile(".*[A-Z].*"); 
        // 小写字母
        inputRegexes[1] = Pattern.compile(".*[a-z].*");
        // 数字
        inputRegexes[2] = Pattern.compile(".*\\d.*");
        // 字符
        inputRegexes[3] = Pattern.compile(".*[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?].*");
    }
   

    private static boolean isMatchingRegex(String input) {
        boolean inputMatches = true;
        for (Pattern inputRegex : inputRegexes) {
            if (!inputRegex
              .matcher(input)
              .matches()) {
                inputMatches = false;
            }
        }
        return inputMatches;
    }

    private static boolean checkString(String input) {
        String specialChars = "~`!@#$%^&*()-_=+\\|[{]};:'\",<.>/?";
        char currentCharacter;
        boolean numberPresent = false;
        boolean upperCasePresent = false;
        boolean lowerCasePresent = false;
        boolean specialCharacterPresent = false;

        for (int i = 0; i < input.length(); i++) {
            currentCharacter = input.charAt(i);
            if (Character.isDigit(currentCharacter)) {
                numberPresent = true;
            } else if (Character.isUpperCase(currentCharacter)) {
                upperCasePresent = true;
            } else if (Character.isLowerCase(currentCharacter)) {
                lowerCasePresent = true;
            } else if (specialChars.contains(String.valueOf(currentCharacter))) {
                specialCharacterPresent = true;
            }
        }

        return numberPresent && upperCasePresent && lowerCasePresent && specialCharacterPresent;
    }

    @Test
    public void givenRegexes_whenMatchingCorrectString_thenMatches() {
        String validInput = "Ab3;";
        assertTrue(isMatchingRegex(validInput));
    }

    @Test
    public void givenRegexes_whenMatchingWrongStrings_thenNotMatching() {
        String invalidInput = "Ab3";
        assertFalse(isMatchingRegex(invalidInput));

        invalidInput = "Ab;";
        assertFalse(isMatchingRegex(invalidInput));

        invalidInput = "A3;";
        assertFalse(isMatchingRegex(invalidInput));

        invalidInput = "b3;";
        assertFalse(isMatchingRegex(invalidInput));
    }

    @Test
    public void givenValidString_whenChecking_thenCorrect() {
        String validInput = "Ab3;";
        assertTrue(checkString(validInput));
    }

    @Test
    public void givenInvalidStrings_whenChecking_thenNotCorrect() {
        String invalidInput = "Ab3";
        assertFalse(checkString(invalidInput));

        invalidInput = "Ab;";
        assertFalse(checkString(invalidInput));

        invalidInput = "A3;";
        assertFalse(checkString(invalidInput));

        invalidInput = "b3;";
        assertFalse(checkString(invalidInput));
    }

    @Test
    public void givenSingleRegex_whenMatchingCorrectString_thenMatches() {
        String validInput = "Ab3;";
        assertTrue(Pattern
          .compile(regex)
          .matcher(validInput)
          .matches());
    }

    @Test
    public void givenSingleRegex_whenMatchingWrongStrings_thenNotMatching() {
        String invalidInput = "Ab3";
        assertFalse(Pattern
          .compile(regex)
          .matcher(invalidInput)
          .matches());

        invalidInput = "Ab;";
        assertFalse(Pattern
          .compile(regex)
          .matcher(invalidInput)
          .matches());

        invalidInput = "A3;";
        assertFalse(Pattern
          .compile(regex)
          .matcher(invalidInput)
          .matches());

        invalidInput = "b3;";
        assertFalse(Pattern
          .compile(regex)
          .matcher(invalidInput)
          .matches());
    }    
}
