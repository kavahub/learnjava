package io.github.kavahub.learnjava.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.util.RemoveDuplicateFromString.*;

/**
 * 
 * {@link RemoveDuplicateFromString} 应用示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class RemoveDuplicateFromStringTest {
 
    private final static String STR1 = "racecar";
    private final static String STR2 = "J2ee programming";
    private final static String STR_EMPTY = "";
    private final static String STR_BLANK = " ";



    @Test
    public void whenUsingCharArray_DuplicatesShouldBeRemovedWithoutKeepingStringOrder() {
        String str1 = removeDuplicatesUsingCharArray(STR1);
        String str2 = removeDuplicatesUsingCharArray(STR2);
        String strEmpty = removeDuplicatesUsingCharArray(STR_EMPTY);
        String strBlank = removeDuplicatesUsingCharArray(STR_BLANK);
        Assertions.assertEquals(" ", strBlank);
        Assertions.assertEquals("", strEmpty);
        Assertions.assertEquals("ecar", str1);
        Assertions.assertEquals("J2e poraming", str2);
    }

    @Test
    public void whenUsingLinkedHashSet_DuplicatesShouldBeRemovedAndItKeepStringOrder() {
        String str1 = removeDuplicatesUsinglinkedHashSet(STR1);
        String str2 = removeDuplicatesUsinglinkedHashSet(STR2);
        String strEmpty = removeDuplicatesUsinglinkedHashSet(STR_EMPTY);
        String strBlank = removeDuplicatesUsinglinkedHashSet(STR_BLANK);
        Assertions.assertEquals(" ", strBlank);
        Assertions.assertEquals("", strEmpty);
        Assertions.assertEquals("race", str1);
        Assertions.assertEquals("J2e progamin", str2);
    }

    @Test
    public void whenUsingSorting_DuplicatesShouldBeRemovedWithoutKeepingStringOrder() {
        String str1 = removeDuplicatesUsingSorting(STR1);
        String str2 = removeDuplicatesUsingSorting(STR2);
        String strEmpty = removeDuplicatesUsingSorting(STR_EMPTY);
        String strBlank = removeDuplicatesUsingSorting(STR_BLANK);
        Assertions.assertEquals(" ", strBlank);
        Assertions.assertEquals("", strEmpty);
        Assertions.assertEquals("acer", str1);
        Assertions.assertEquals(" 2Jaegimnopr", str2);
    }

    @Test
    public void whenUsingHashSet_DuplicatesShouldBeRemovedWithoutKeepingStringOrder() {
        String str1 = removeDuplicatesUsingHashSet(STR1);
        String str2 = removeDuplicatesUsingHashSet(STR2);
        String strEmpty = removeDuplicatesUsingHashSet(STR_EMPTY);
        String strBlank = removeDuplicatesUsingHashSet(STR_BLANK);
        Assertions.assertEquals(" ", strBlank);
        Assertions.assertEquals("", strEmpty);
        Assertions.assertEquals("arce", str1);
        Assertions.assertEquals(" pa2regiJmno", str2);
    }

    @Test
    public void whenUsingIndexOf_DuplicatesShouldBeRemovedWithoutKeepingStringOrder() {
        String str1 = removeDuplicatesUsingIndexOf(STR1);
        String str2 = removeDuplicatesUsingIndexOf(STR2);
        String strEmpty = removeDuplicatesUsingIndexOf(STR_EMPTY);
        String strBlank = removeDuplicatesUsingIndexOf(STR_BLANK);
        Assertions.assertEquals(" ", strBlank);
        Assertions.assertEquals("", strEmpty);
        Assertions.assertEquals("ecar", str1);
        Assertions.assertEquals("J2e poraming", str2);
    }

    @Test
    public void whenUsingJava8_DuplicatesShouldBeRemovedAndItKeepStringOrder() {
        String str1 = removeDuplicatesUsingDistinct(STR1);
        String str2 = removeDuplicatesUsingDistinct(STR2);
        String strEmpty = removeDuplicatesUsingDistinct(STR_EMPTY);
        String strBlank = removeDuplicatesUsingDistinct(STR_BLANK);
        Assertions.assertEquals(" ", strBlank);
        Assertions.assertEquals("", strEmpty);
        Assertions.assertEquals("race", str1);
        Assertions.assertEquals("J2e progamin", str2);
    }   
}
