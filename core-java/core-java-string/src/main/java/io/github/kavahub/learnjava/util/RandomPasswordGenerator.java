package io.github.kavahub.learnjava.util;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import lombok.experimental.UtilityClass;

/**
 * 密码生成器
 * 
 */
@UtilityClass
public class RandomPasswordGenerator {
    /**
     * Special characters allowed in password.
     */
    public static final String ALLOWED_SPL_CHARACTERS = "!@#$%^&*()_+";

    public static final String ERROR_CODE = "ERRONEOUS_SPECIAL_CHARS";

    // SecureRandom 提供加密的强随机数生成器 (RNG)，要求种子必须是不可预知的，产生非确定性输出。
    // SecureRandom 也提供了与实现无关的算法，因此，调用方（应用程序代码）会请求特定的 RNG 算法并将它传回到该算法的 SecureRandom
    // 对象中
    private Random random = new SecureRandom();

    /**
     * Passay库, 功能如下：
     * 
     * Password validation Enforce password policy by validating candidate passwords
     * against a configurable rule set. Passay provides a comprehensive set of rules
     * for common cases and supports extension through a simple rule interface.
     * 
     * Password generation Generate passwords using a configurable rule set. The
     * password generator is extensible like all Passay components.
     * 
     * Command line tools Automate password policy enforcement and support tooling
     * scenarios using the command line interface.
     * 
     * @return
     */
    public String generatePassayPassword() {
        PasswordGenerator gen = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);
        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);
        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);
        CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return ERROR_CODE;
            }

            public String getCharacters() {
                return ALLOWED_SPL_CHARACTERS;
            }
        };
        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(2);
        String password = gen.generatePassword(10, splCharRule, lowerCaseRule, upperCaseRule, digitRule);
        return password;
    }

    /**
     * Commons-text库
     * 
     * @return
     */
    public String generateCommonTextPassword() {
        String pwString = generateRandomSpecialCharacters(2).concat(generateRandomNumbers(2))
                .concat(generateRandomAlphabet(2, true)).concat(generateRandomAlphabet(2, false))
                .concat(generateRandomCharacters(2));
        List<Character> pwChars = pwString.chars().mapToObj(data -> (char) data).collect(Collectors.toList());
        Collections.shuffle(pwChars);
        String password = pwChars.stream().collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        return password;
    }

    /**
     * Commons-lang库
     * 
     * @return
     */
    public String generateCommonsLang3Password() {
        String upperCaseLetters = RandomStringUtils.random(2, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(2, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(2);
        String specialChar = RandomStringUtils.random(2, 33, 47, false, false);
        String totalChars = RandomStringUtils.randomAlphanumeric(2);
        String combinedChars = upperCaseLetters.concat(lowerCaseLetters).concat(numbers).concat(specialChar)
                .concat(totalChars);
        List<Character> pwdChars = combinedChars.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        Collections.shuffle(pwdChars);
        String password = pwdChars.stream().collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        return password;
    }

    /**
     * java库
     * 
     * @return
     */
    public String generateSecureRandomPassword() {
        Stream<Character> pwdStream = Stream.concat(getRandomNumbers(2), Stream.concat(getRandomSpecialChars(2),
                Stream.concat(getRandomAlphabets(2, true), getRandomAlphabets(4, false))));
        List<Character> charList = pwdStream.collect(Collectors.toList());
        Collections.shuffle(charList);
        String password = charList.stream().collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        return password;
    }

    private String generateRandomSpecialCharacters(int length) {
        RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange(33, 45).build();
        return pwdGenerator.generate(length);
    }

    private String generateRandomNumbers(int length) {
        RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange(48, 57).build();
        return pwdGenerator.generate(length);
    }

    private String generateRandomCharacters(int length) {
        RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange(48, 57).build();
        return pwdGenerator.generate(length);
    }

    private String generateRandomAlphabet(int length, boolean lowerCase) {
        int low;
        int hi;
        if (lowerCase) {
            low = 97;
            hi = 122;
        } else {
            low = 65;
            hi = 90;
        }
        RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange(low, hi).build();
        return pwdGenerator.generate(length);
    }

    private Stream<Character> getRandomAlphabets(int count, boolean upperCase) {
        IntStream characters = null;
        if (upperCase) {
            characters = random.ints(count, 65, 90);
        } else {
            characters = random.ints(count, 97, 122);
        }
        return characters.mapToObj(data -> (char) data);
    }

    private Stream<Character> getRandomNumbers(int count) {
        IntStream numbers = random.ints(count, 48, 57);
        return numbers.mapToObj(data -> (char) data);
    }

    private Stream<Character> getRandomSpecialChars(int count) {
        IntStream specialChars = random.ints(count, 33, 45);
        return specialChars.mapToObj(data -> (char) data);
    }
}
