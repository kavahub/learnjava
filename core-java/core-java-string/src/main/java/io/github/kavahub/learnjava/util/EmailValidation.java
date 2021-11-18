package io.github.kavahub.learnjava.util;

import java.util.regex.Pattern;

import lombok.experimental.UtilityClass;

/**
 * 邮件格式校验
 * 
 */
@UtilityClass
public class EmailValidation {

    public boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
            .matcher(emailAddress)
            .matches();
    }   
}
