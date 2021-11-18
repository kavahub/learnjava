package io.github.kavahub.learnjava;

import java.nio.charset.Charset;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * 生成随机字符串
 * 
 */
@Slf4j
public class RandomStringsTest {
    @Test
    public void givenUsingPlainJava_whenGeneratingRandomStringUnbounded_thenCorrect() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));

        log.debug(generatedString);
    }

    @Test
    public void givenUsingPlainJava_whenGeneratingRandomStringBounded_thenCorrect() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);

        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        log.debug(generatedString);
    }

    /**
     * 字母的
     */
    @Test
    public void givenUsingJava8_whenGeneratingRandomAlphabeticString_thenCorrect() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
            .limit(targetStringLength)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();

        log.debug(generatedString);
    }

    /**
     * 含有字母和数字的
     */
    @Test
    public void givenUsingJava8_whenGeneratingRandomAlphanumericString_thenCorrect() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
            .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
            .limit(targetStringLength)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();

        log.debug(generatedString);
    }

    @Test
    public void givenUsingApache_whenGeneratingRandomString_thenCorrect() {
        String generatedString = RandomStringUtils.random(10);

        log.debug(generatedString);
    }

    @Test
    public void givenUsingApache_whenGeneratingRandomAlphabeticString_thenCorrect() {
        String generatedString = RandomStringUtils.randomAlphabetic(10);

        log.debug(generatedString);
    }

    @Test
    public void givenUsingApache_whenGeneratingRandomAlphanumericString_thenCorrect() {
        String generatedString = RandomStringUtils.randomAlphanumeric(10);

        log.debug(generatedString);
    }

    @Test
    public void givenUsingApache_whenGeneratingRandomStringBounded_thenCorrect() {
        int length = 10;
        // 包含字母
        boolean useLetters = true;
        // 包含数字
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

        log.debug(generatedString);
    }    
}
