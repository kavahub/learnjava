package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.StringJoiner;
import java.util.function.BiFunction;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class StringTest {
    @Test
    public void whenEmptyOrBlank() {
        assertTrue("".isEmpty());
        assertTrue("".isBlank());

        assertFalse(" ".isEmpty());
        assertTrue(" ".isBlank());

        assertTrue(StringUtils.isBlank(""));
        assertTrue(StringUtils.isBlank(" "));

        assertTrue(StringUtils.isEmpty(""));
        assertFalse(StringUtils.isEmpty(" "));

        assertTrue(StringUtils.isBlank(null));
        assertTrue(StringUtils.isEmpty(null));
    }  

    @Test
    public void whenUsingInbuildMethods_thenStringReversed() {
        String reversed = new StringBuilder("learnjava").reverse().toString();
        assertEquals("avajnrael", reversed);
    }  

    @Test
    public void whenUsingStringJoiner_thenStringsJoined() {
        StringJoiner joiner = new StringJoiner(",", "[", "]");
        joiner.add("Red")
          .add("Green")
          .add("Blue");
         
        assertEquals(joiner.toString(), "[Red,Green,Blue]");
    }

    @Test
    public void whenCallingStringIntern_thenStringsInterned() {
        String s1 = "Java";
        String s2 = new String("Java");
        // String.intern()是一个Native方法，底层调用C++的 StringTable::intern 方法. 
        // 当调用 intern 方法时，如果常量池中已经该字符串，则返回池中的字符串；否则将此字符串添加到常量池中，并返回字符串的引用。
        String s3 = new String("Java").intern();
         
        assertFalse(s1 == s2);
        assertTrue(s1 == s3);
    }

    @Test
    public void whenTestAnagrams_thenTestingCorrectly() {
        BiFunction<String, String, Boolean> isAnagram = (s1, s2) -> {
                if(s1.length() != s2.length())
                return false;
                
            char[] arr1 = s1.toCharArray();
            char[] arr2 = s2.toCharArray();
            
            Arrays.sort(arr1);
            Arrays.sort(arr2);
            
            return Arrays.equals(arr1, arr2);
        };


        assertTrue(isAnagram.apply("car", "arc"));
        assertTrue(isAnagram.apply("west", "stew"));
        assertFalse(isAnagram.apply("west", "east"));
    }

    @Test
    public void whenUsingLocal_thenCorrectResultsForDifferentLocale() {
        Locale usLocale = Locale.US;
        BigDecimal number = new BigDecimal(102_300.456d);
         
        NumberFormat usNumberFormat = NumberFormat.getCurrencyInstance(usLocale); 
        assertEquals(usNumberFormat.format(number), "$102,300.46");
    }
}
