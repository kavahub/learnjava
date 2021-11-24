package io.github.kavahub.learnjava.use;

import static io.github.kavahub.learnjava.use.UseDateTimeFormatter.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.FormatStyle;
import java.util.Locale;

import org.junit.jupiter.api.Test;

public class UseDateTimeFormatterTest {
    private final LocalDateTime localDateTime = LocalDateTime.of(2015, Month.JANUARY, 25, 6, 30);

    @Test
    public void givenALocalDate_whenFormattingAsIso_thenPass() {
        String result = formatAsIsoDate(localDateTime);

        assertThat(result).isEqualTo("2015-01-25");
    }

    @Test
    public void givenALocalDate_whenFormattingWithPattern_thenPass() {
        String result = formatCustom(localDateTime, "yyyy/MM/dd");

        assertThat(result).isEqualTo("2015/01/25");
    }

    @Test
    public void givenALocalDate_whenFormattingWithStyleAndLocale_thenPass() {
        String result = formatWithStyleAndLocale(localDateTime, FormatStyle.MEDIUM, Locale.UK);
        assertThat(result).isEqualTo("25 Jan 2015, 06:30:00");

    }   

    @Test
    public void givenALocalDate_whenFormattingWithStyleAllAndLocale_thenPass() {
        String result = formatWithStyleAndLocale(localDateTime, FormatStyle.MEDIUM, Locale.CHINA);
        assertThat(result).isEqualTo("2015年1月25日 上午6:30:00");

        // 不支持
        //result = subject.formatWithStyleAndLocale(localDateTime, FormatStyle.FULL, Locale.CHINA);
        //result = subject.formatWithStyleAndLocale(localDateTime, FormatStyle.LONG, Locale.CHINA);
        
        result = formatWithStyleAndLocale(localDateTime, FormatStyle.SHORT, Locale.CHINA);
        assertThat(result).isEqualTo("2015/1/25 上午6:30");
    }   
}
