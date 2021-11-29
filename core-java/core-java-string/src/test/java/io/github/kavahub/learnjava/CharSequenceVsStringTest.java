package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * {@link CharSequence} 与 {@link String} 应用比较
 * 
 * <p>
 * CharSequence是一个描述字符串结构的接口
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class CharSequenceVsStringTest {
    @Test
    public void givenUsingString_whenInstantiatingString_thenWrong() {
        CharSequence firstString = "learnjava";
        String secondString = "learnjava";

        assertEquals(firstString, secondString);
    }

    @Test
    public void givenIdenticalCharSequences_whenCastToString_thenEqual() {
        CharSequence charSequence1 = "learnjava_1";
        CharSequence charSequence2 = "learnjava_2";

        assertTrue(charSequence1.toString().compareTo(charSequence2.toString()) < 0);
    }

    @Test
    public void givenString_whenAppended_thenUnmodified() {
        String test = "a";
        int firstAddressOfTest = System.identityHashCode(test);
        test += "b";
        int secondAddressOfTest = System.identityHashCode(test);

        assertNotEquals(firstAddressOfTest, secondAddressOfTest);
    }

    @Test
    public void givenStringBuilder_whenAppended_thenModified() {
        StringBuilder test = new StringBuilder();
        test.append("a");
        int firstAddressOfTest = System.identityHashCode(test);
        test.append("b");
        int secondAddressOfTest = System.identityHashCode(test);

        assertEquals(firstAddressOfTest, secondAddressOfTest);
    }   
}
