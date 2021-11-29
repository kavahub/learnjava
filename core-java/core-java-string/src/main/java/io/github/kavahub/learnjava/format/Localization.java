package io.github.kavahub.learnjava.format;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 国际化
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
public class Localization {
    public static String getLabel(Locale locale) {
        final ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        return bundle.getString("label");
    }

    public static void run(List<Locale> locales) {
        locales.forEach(locale -> log.info(getLabel(locale)));
    }   
}
