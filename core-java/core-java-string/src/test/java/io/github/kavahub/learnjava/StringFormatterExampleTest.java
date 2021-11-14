package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.IllegalFormatCodePointException;

import org.junit.jupiter.api.Test;

/**
 * 
转换符	说明											示例
%s		字符串类型										"mingrisoft"
%c		字符类型										'm'
%b		布尔类型										true
%d		整数类型（十进制）								99
%x		整数类型（十六进制）							FF
%o		整数类型（八进制）								77
%f		浮点类型										99.99
%a		十六进制浮点类型								FF.35AE
%e		指数类型										9.38e+5
%g		通用浮点类型（f和e类型中较短的）
%h		散列码
%%		百分比类型
%n		换行符
%tx		日期与时间类型（x代表不同的日期与时间转换符
 * 
 */
public class StringFormatterExampleTest {
    @Test
    public void givenString_whenFormatSpecifierForCalendar_thenGotExpected() {
        // Syntax of Format Specifiers for Date/Time Representation
        Calendar c = new GregorianCalendar(2017, 11, 10);
        String s = String.format("The date is: %tm %1$te,%1$tY", c);

        assertEquals("The date is: 12 10,2017", s);
    }

    @Test
    public void givenString_whenGeneralConversion_thenConvertedString() {
        // General Conversions
        String s = String.format("The correct answer is %s", false);
        assertEquals("The correct answer is false", s);

        Boolean b = null;
        s = String.format("The correct answer is %b", b);
        assertEquals("The correct answer is false", s);

        s = String.format("The correct answer is %B", true);
        assertEquals("The correct answer is TRUE", s);
    }

    @Test
    public void givenString_whenCharConversion_thenConvertedString() {
        // Character Conversions
        String s = String.format("The correct answer is %c", 'a');
        assertEquals("The correct answer is a", s);

        Character c = null;
        s = String.format("The correct answer is %c", c);
        assertEquals("The correct answer is null", s);

        s = String.format("The correct answer is %C", 'b');
        assertEquals("The correct answer is B", s);

        s = String.format("The valid unicode character: %c", 0x0400);
        assertTrue(Character.isValidCodePoint(0x0400));
        assertEquals("The valid unicode character: Ѐ", s);
    }

    @Test
    public void givenString_whenIllegalCodePointForConversion_thenError() {
        assertThrows(IllegalFormatCodePointException.class,
                () -> String.format("The valid unicode character: %c", 0x11FFFF));
    }

    @Test
    public void givenString_whenNumericIntegralConversion_thenConvertedString() {
        // Numeric Integral Conversions
        String s = String.format("The number 25 in decimal = %d", 25);
        assertEquals("The number 25 in decimal = 25", s);

        s = String.format("The number 25 in octal = %o", 25);
        assertEquals("The number 25 in octal = 31", s);

        s = String.format("The number 25 in hexadecimal = %x", 25);
        assertEquals("The number 25 in hexadecimal = 19", s);
    }

    @Test
    public void givenString_whenNumericFloatingConversion_thenConvertedString() {
        // Numeric Floating-point Conversions
        String s = String.format("The computerized scientific format of 10000.00 " + "= %e", 10000.00);
        assertEquals("The computerized scientific format of 10000.00 = 1.000000e+04", s);

        s = String.format("The decimal format of 10.019 = %f", 10.019);
        assertEquals("The decimal format of 10.019 = 10.019000", s);
    }

    @Test
    public void givenString_whenLineSeparatorConversion_thenConvertedString() {
        // Line Separator Conversion
        String s = String.format("First Line %nSecond Line");
        assertEquals("First Line " + System.getProperty("line.separator") + "Second Line", s);
    }

    @Test
    public void givenString_whenSpecifyFlag_thenGotFormattedString() {
        // Without left-justified flag
        String s = String.format("Without left justified flag: %5d", 25);
        assertEquals("Without left justified flag:    25", s);

        // Using left-justified flag
        s = String.format("With left justified flag: %-5d", 25);
        assertEquals("With left justified flag: 25   ", s);
    }

    @Test
    public void givenString_whenSpecifyPrecision_thenGotExpected() {
        // Precision
        String s = String.format("Output of 25.09878 with Precision 2: %.2f", 25.09878);
        assertEquals("Output of 25.09878 with Precision 2: 25.10", s);

        s = String.format("Output of general conversion type with Precision 2: %.2b", true);
        assertEquals("Output of general conversion type with Precision 2: tr", s);
    }

    @Test
    public void givenString_whenSpecifyArgumentIndex_thenGotExpected() {
        Calendar c = new GregorianCalendar(2017, 11, 10);
        // Argument_Index
        String s = String.format("The date is: %tm %1$te,%1$tY", c);
        assertEquals("The date is: 12 10,2017", s);

        s = String.format("The date is: %tm %<te,%<tY", c);
        assertEquals("The date is: 12 10,2017", s);
    }

    @Test
    public void givenAppendable_whenCreateFormatter_thenFormatterWorksOnAppendable() {
        // Using String Formatter with Appendable
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        formatter.format("I am writting to a %s Instance.", sb.getClass());
        assertEquals("I am writting to a class java.lang.StringBuilder Instance.", sb.toString());
        formatter.close();
    }

    @Test
    public void givenString_whenNoArguments_thenExpected() {
        // Using String Formatter without arguments
        String s = String.format("John scored 90%% in Fall semester");
        assertEquals("John scored 90% in Fall semester", s);
    }
}
