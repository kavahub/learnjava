package io.github.kavahub.learnjava;

import static io.github.kavahub.learnjava.util.PrimitivesArrayToListConverter.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link PrimitivesArrayToListConverter} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class PrimitivesArrayToListConverterTest {
    @Test
    public void givenArrayWithPrimitives_whenIterativeConvert_thenArrayGetsConverted() {
        assertEquals(Arrays.asList(1,2,3,4), iterateConvert(new int[]{1,2,3,4}));
    }

    @Test
    public void givenArrayWithPrimitives_whenStreamConvert_thenArrayGetsConverted() {
        assertEquals(Arrays.asList(1,2,3,4), streamConvert(new int[]{1,2,3,4}));
    }

    @Test
    public void givenArrayWithPrimitives_whenIntStreamConvert_thenArrayGetsConverted() {
        assertEquals(Arrays.asList(1,2,3,4), streamConvertIntStream(new int[]{1,2,3,4}));
    }

    @Test
    public void givenArrayWithPrimitives_whenGuavaConvert_thenArrayGetsConverted() {
        assertEquals(Arrays.asList(1,2,3,4), guavaConvert(new int[]{1,2,3,4}));
    }

    @Test
    public void givenArrayWithPrimitives_whenApacheCommonConvert_thenArrayGetsConverted() {
        assertEquals(Arrays.asList(1,2,3,4), apacheCommonConvert(new int[]{1,2,3,4}));
    }
}
