package io.github.kavahub.learnjava.use;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.use.UseToInstant.*;

public class UseToInstantTest {
    @Test
    public void givenAGregorianCalenderDate_whenConvertingToLocalDate_thenAsExpected() {
        GregorianCalendar givenCalender = new GregorianCalendar(2018, Calendar.JULY, 28);

        LocalDateTime localDateTime = convertDateToLocalDate(givenCalender);

        assertThat(localDateTime).isEqualTo("2018-07-28T00:00:00");
    }

    @Test
    public void givenADate_whenConvertingToLocalDate_thenAsExpected() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Date givenDate = Date.from(currentDateTime.atZone(ZoneId.systemDefault()).toInstant());

        LocalDateTime localDateTime = convertDateToLocalDate(givenDate);

        // 在转换的过程中，毫秒精确位丢失。  Date类的毫秒数只保留三位，所以不能相等
        // 参考： givenInstant_whenDateFromToInstant_thenNotEqual

        // assertThat(localDateTime).isEqualTo(currentDateTime);
        assertTrue(Duration.between(currentDateTime, localDateTime).toSeconds() == -1);
    } 
    
    @Test
    public void givenInstant_whenDateFromAndToInstant_thenNotEqual() {
        Instant nowBefore =  Instant.now();
        Instant nowAfter = Date.from(nowBefore).toInstant();

        assertThat(nowBefore).isNotEqualTo(nowAfter);
        assertTrue(Duration.between(nowBefore, nowAfter).toSeconds() == -1);

    } 
}
