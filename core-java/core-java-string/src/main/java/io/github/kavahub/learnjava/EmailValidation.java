package io.github.kavahub.learnjava;

import java.util.regex.Pattern;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EmailValidation {

    public boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
            .matcher(emailAddress)
            .matches();
    }   
}
