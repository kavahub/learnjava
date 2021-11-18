package io.github.kavahub.learnjava.format;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import lombok.extern.slf4j.Slf4j;

/**
 * Java格式化
 */
@Slf4j
public class JavaSEFormat {
    public static String getLabel(Locale locale, Object[] data) {
        ResourceBundle bundle = ResourceBundle.getBundle("formats", locale);
        final String pattern = bundle.getString("label");
        final MessageFormat formatter = new MessageFormat(pattern, locale);
        return formatter.format(data);
    }

    public static void run(List<Locale> locales) {
        log.info("Java formatter");
        final Date date = new Date(System.currentTimeMillis());
        locales.forEach(locale -> log.info(getLabel(locale, new Object[] { date, "Alice", 0 })));
        locales.forEach(locale -> log.info(getLabel(locale, new Object[] { date, "Alice", 2 })));
    } 
}
