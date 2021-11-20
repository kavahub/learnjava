package io.github.kavahub.learnjava.reflect;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * 执行私有方法
 */
public class InvokePrivateMethodsTest {
    private final long[] someLongArray = new long[] { 1L, 2L, 1L, 4L, 2L };

    @Test
    void whenSearchingForLongValueInSubsequenceUsingReflection_thenTheCorrectIndexOfTheValueIsReturned() throws Exception {
        Method indexOfMethod = LongArrayUtil.class.getDeclaredMethod("indexOf", long[].class, long.class, int.class, int.class);
        indexOfMethod.setAccessible(true);

        assertEquals(2, indexOfMethod.invoke(LongArrayUtil.class, someLongArray, 1L, 1, someLongArray.length), "The index should be 2.");
    }

    @Test
    void whenSearchingForLongValueInSubsequenceUsingSpring_thenTheCorrectIndexOfTheValueIsReturned() throws Exception {
        int indexOfSearchTarget = ReflectionTestUtils.invokeMethod(LongArrayUtil.class, "indexOf", someLongArray, 1L, 1, someLongArray.length);
        assertEquals(2, indexOfSearchTarget, "The index should be 2.");
    }   
    
    public static class LongArrayUtil {

        public static int indexOf(long[] array, long target) {
            return indexOf(array, target, 0, array.length);
        }
    
        private static int indexOf(long[] array, long target, int start, int end) {
            for (int i = start; i < end; i++) {
                if (array[i] == target) {
                    return i;
                }
            }
            return -1;
        }
    
    }
}
