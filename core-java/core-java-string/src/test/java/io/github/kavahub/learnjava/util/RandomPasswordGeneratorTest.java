package io.github.kavahub.learnjava.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.util.RandomPasswordGenerator.*;

/**
 * 
 * {@link RandomPasswordGenerator} 应用示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class RandomPasswordGeneratorTest {

    @Test
    public void whenPasswordGeneratedUsingPassay_thenSuccessful() {
        String password = generatePassayPassword();
        int specialCharCount = 0;
        for (char c : password.toCharArray()) {
            if (c >= 33 || c <= 47) {
                specialCharCount++;
            }
        }
        assertTrue(specialCharCount >= 2);
    }

    @Test
    public void whenPasswordGeneratedUsingCommonsText_thenSuccessful() {
        String password = generateCommonTextPassword();
        int lowerCaseCount = 0;
        for (char c : password.toCharArray()) {
            if (c >= 97 || c <= 122) {
                lowerCaseCount++;
            }
        }
        assertTrue(lowerCaseCount >= 2);
    }

    @Test
    public void whenPasswordGeneratedUsingCommonsLang3_thenSuccessful() {
        String password = generateCommonsLang3Password();
        int numCount = 0;
        for (char c : password.toCharArray()) {
            if (c >= 48 || c <= 57) {
                numCount++;
            }
        }
        assertTrue(numCount >= 2);
    }

    @Test
    public void whenPasswordGeneratedUsingSecureRandom_thenSuccessful() {
        String password = generateSecureRandomPassword();
        int specialCharCount = 0;
        for (char c : password.toCharArray()) {
            if (c >= 33 || c <= 47) {
                specialCharCount++;
            }
        }
        assertTrue(specialCharCount >= 2);
    }    
}
