package io.github.kavahub.learnjava.util;

import java.util.regex.Pattern;

import lombok.experimental.UtilityClass;

/**
 * 
 * 邮件地址格式校验
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class EmailValidation {

    public boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
            .matcher(emailAddress)
            .matches();
    }   
}
