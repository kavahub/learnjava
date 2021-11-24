package io.github.kavahub.learnjava.use;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import lombok.experimental.UtilityClass;

/**
 * {@link LocalDateTime} 格式化
 * 
 * <ul>
 * <li> GMT: 格林尼治所在地的标准时间 </li>
 * <li> UTC: 协调世界时，又称世界统一时间、世界标准时间、国际协调时间 </li>
 * <li> CST: CST可视为美国、澳大利亚、古巴或中国的标准时间 </li>
 * <li> ISO: 是一种时间的表示方法 </li>
 * </ul>
 * 
 * 说明：GMT是前世界标准时,UTC是现世界标准时，可以认为是一样的, 只不过UTC更加精准
 */
@UtilityClass
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
