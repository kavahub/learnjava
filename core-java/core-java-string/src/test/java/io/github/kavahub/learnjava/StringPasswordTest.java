package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.github.kavahub.learnjava.util.RandomPasswordGenerator;

public class StringPasswordTest {

    @Test
    public void whenPasswordGeneratedUsingPassay_thenSuccessful() {
        String password = RandomPasswordGenerator.generatePassayPassword();
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
        String password = RandomPasswordGenerator.generateCommonTextPassword();
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
        String password = RandomPasswordGenerator.generateCommonsLang3Password();
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
        String password = RandomPasswordGenerator.generateSecureRandomPassword();
        int specialCharCount = 0;
        for (char c : password.toCharArray()) {
            if (c >= 33 || c <= 47) {
                specialCharCount++;
            }
        }
        assertTrue(specialCharCount >= 2);
    }    
}
