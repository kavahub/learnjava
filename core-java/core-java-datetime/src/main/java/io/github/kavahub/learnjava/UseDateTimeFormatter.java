package io.github.kavahub.learnjava;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * 
 */
public class UseDateTimeFormatter {
    public String formatAsIsoDate(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ISO_DATE);
    }

    public String formatCustom(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * ofLocalizedDateTime只支持MEDIUM, SHORT
     * 
     * @see <a href="https://stackoverflow.com/questions/59531046/java-time-datetimeexception-unable-to-extract-zoneid-from-temporal">StackOverflow</a>
     * 
     * @param localDateTime
     * @param formatStyle
     * @param locale
     * @return
     */
    public String formatWithStyleAndLocale(LocalDateTime localDateTime, FormatStyle formatStyle, Locale locale) {
        return localDateTime.format(DateTimeFormatter.ofLocalizedDateTime(formatStyle)
            .withLocale(locale));
    }   
}
