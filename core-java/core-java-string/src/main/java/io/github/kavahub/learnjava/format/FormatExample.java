package io.github.kavahub.learnjava.format;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * 
 * 按地区格式化字符串
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class FormatExample {
    public static void main(String[] args) {
        List<Locale> locales = Arrays.asList(new Locale[] { Locale.ENGLISH, Locale.ITALY, Locale.FRANCE, Locale.forLanguageTag("pl-PL") });
        Localization.run(locales);
        JavaSEFormat.run(locales);
        ICUFormat.run(locales);
    } 
}
