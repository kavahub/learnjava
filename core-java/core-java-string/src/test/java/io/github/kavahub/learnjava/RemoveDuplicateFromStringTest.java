package io.github.kavahub.learnjava;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 注意，个功能返回字符串的顺序不相同
 */
public class RemoveDuplicateFromStringTest {
 
    private final static String STR1 = "racecar";
    private final static String STR2 = "J2ee programming";
    private final static String STR_EMPTY = "";
    private final static String STR_BLANK = " ";



    @Test
    public void whenUsingCharArray_DuplicatesShouldBeRemovedWithoutKeepingStringOrder() {
        String str1 = RemoveDuplicateFromString.removeDuplicatesUsingCharArray(STR1);
        String str2 = RemoveDuplicateFromString.removeDuplicatesUsingCharArray(STR2);
        String strEmpty = RemoveDuplicateFromString.removeDuplicatesUsingCharArray(STR_EMPTY);
        String strBlank = RemoveDuplicateFromString.removeDuplicatesUsingCharArray(STR_BLANK);
        Assertions.assertEquals(" ", strBlank);
        Assertions.assertEquals("", strEmpty);
        Assertions.assertEquals("ecar", str1);
        Assertions.assertEquals("J2e poraming", str2);
    }

    @Test
    public void whenUsingLinkedHashSet_DuplicatesShouldBeRemovedAndItKeepStringOrder() {
        String str1 = RemoveDuplicateFromString.removeDuplicatesUsinglinkedHashSet(STR1);
        String str2 = RemoveDuplicateFromString.removeDuplicatesUsinglinkedHashSet(STR2);
        String strEmpty = RemoveDuplicateFromString.removeDuplicatesUsinglinkedHashSet(STR_EMPTY);
        String strBlank = RemoveDuplicateFromString.removeDuplicatesUsinglinkedHashSet(STR_BLANK);
        Assertions.assertEquals(" ", strBlank);
        Assertions.assertEquals("", strEmpty);
        Assertions.assertEquals("race", str1);
        Assertions.assertEquals("J2e progamin", str2);
    }

    @Test
    public void whenUsingSorting_DuplicatesShouldBeRemovedWithoutKeepingStringOrder() {
        String str1 = RemoveDuplicateFromString.removeDuplicatesUsingSorting(STR1);
        String str2 = RemoveDuplicateFromString.removeDuplicatesUsingSorting(STR2);
        String strEmpty = RemoveDuplicateFromString.removeDuplicatesUsingSorting(STR_EMPTY);
        String strBlank = RemoveDuplicateFromString.removeDuplicatesUsingSorting(STR_BLANK);
        Assertions.assertEquals(" ", strBlank);
        Assertions.assertEquals("", strEmpty);
        Assertions.assertEquals("acer", str1);
        Assertions.assertEquals(" 2Jaegimnopr", str2);
    }

    @Test
    public void whenUsingHashSet_DuplicatesShouldBeRemovedWithoutKeepingStringOrder() {
        String str1 = RemoveDuplicateFromString.removeDuplicatesUsingHashSet(STR1);
        String str2 = RemoveDuplicateFromString.removeDuplicatesUsingHashSet(STR2);
        String strEmpty = RemoveDuplicateFromString.removeDuplicatesUsingHashSet(STR_EMPTY);
        String strBlank = RemoveDuplicateFromString.removeDuplicatesUsingHashSet(STR_BLANK);
        Assertions.assertEquals(" ", strBlank);
        Assertions.assertEquals("", strEmpty);
        Assertions.assertEquals("arce", str1);
        Assertions.assertEquals(" pa2regiJmno", str2);
    }

    @Test
    public void whenUsingIndexOf_DuplicatesShouldBeRemovedWithoutKeepingStringOrder() {
        String str1 = RemoveDuplicateFromString.removeDuplicatesUsingIndexOf(STR1);
        String str2 = RemoveDuplicateFromString.removeDuplicatesUsingIndexOf(STR2);
        String strEmpty = RemoveDuplicateFromString.removeDuplicatesUsingIndexOf(STR_EMPTY);
        String strBlank = RemoveDuplicateFromString.removeDuplicatesUsingIndexOf(STR_BLANK);
        Assertions.assertEquals(" ", strBlank);
        Assertions.assertEquals("", strEmpty);
        Assertions.assertEquals("ecar", str1);
        Assertions.assertEquals("J2e poraming", str2);
    }

    @Test
    public void whenUsingJava8_DuplicatesShouldBeRemovedAndItKeepStringOrder() {
        String str1 = RemoveDuplicateFromString.removeDuplicatesUsingDistinct(STR1);
        String str2 = RemoveDuplicateFromString.removeDuplicatesUsingDistinct(STR2);
        String strEmpty = RemoveDuplicateFromString.removeDuplicatesUsingDistinct(STR_EMPTY);
        String strBlank = RemoveDuplicateFromString.removeDuplicatesUsingDistinct(STR_BLANK);
        Assertions.assertEquals(" ", strBlank);
        Assertions.assertEquals("", strEmpty);
        Assertions.assertEquals("race", str1);
        Assertions.assertEquals("J2e progamin", str2);
    }   
}
