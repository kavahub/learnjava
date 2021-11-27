package io.github.kavahub.learnjava.common.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Map;

import com.google.common.collect.ImmutableRangeMap;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link RangeMap} 接口是一种集合类型，它将不相交、且不为空的 <code>Range</code>（key）映射给一个值（Value），
 *  <code>RangeMap</code> 不可以将相邻的区间合并，即使这个区间映射的值是一样的，实现类如下：
 * <ul>
 * <li> mmutableRangeMap: 不可变的集合</li>
 * <li> TreeRangeMap: 键是有序的</li>
 * </ul>
 *
 * <p>
 * <code>Range<、code> 定义了连续跨度的范围边界，这个连续跨度是一个可以比较的类型(Comparable type)。比如1到100之间的整型数据。
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class RangeMapTest {
    @Test
    public void givenRangeMap_whenQueryWithinRange_returnsSucessfully() {
        final RangeMap<Integer, String> experienceRangeDesignationMap = TreeRangeMap.create();

        experienceRangeDesignationMap.put(Range.closed(0, 2), "Associate");
        experienceRangeDesignationMap.put(Range.closed(3, 5), "Senior Associate");
        experienceRangeDesignationMap.put(Range.closed(6, 8), "Vice President");
        experienceRangeDesignationMap.put(Range.closed(9, 15), "Executive Director");
        experienceRangeDesignationMap.put(Range.closed(16, 30), "Managing Director");

        assertEquals("Vice President", experienceRangeDesignationMap.get(6));
        assertEquals("Executive Director", experienceRangeDesignationMap.get(15));
    }

    @Test
    public void givenRangeMap_whenQueryOutsideRange_returnsNull() {
        final RangeMap<Integer, String> experienceRangeDesignationMap = TreeRangeMap.create();

        experienceRangeDesignationMap.put(Range.closed(0, 2), "Associate");
        experienceRangeDesignationMap.put(Range.closed(3, 5), "Senior Associate");
        experienceRangeDesignationMap.put(Range.closed(6, 8), "Vice President");
        experienceRangeDesignationMap.put(Range.closed(9, 15), "Executive Director");
        experienceRangeDesignationMap.put(Range.closed(16, 30), "Managing Director");

        assertNull(experienceRangeDesignationMap.get(31));
    }

    @Test
    public void givenRangeMap_whenRemoveRangeIsCalled_removesSucessfully() {
        final RangeMap<Integer, String> experienceRangeDesignationMap = TreeRangeMap.create();

        experienceRangeDesignationMap.put(Range.closed(0, 2), "Associate");
        experienceRangeDesignationMap.put(Range.closed(3, 5), "Senior Associate");
        experienceRangeDesignationMap.put(Range.closed(6, 8), "Vice President");
        experienceRangeDesignationMap.put(Range.closed(9, 15), "Executive Director");
        experienceRangeDesignationMap.put(Range.closed(16, 30), "Managing Director");
        experienceRangeDesignationMap.remove(Range.closed(8, 15));
        experienceRangeDesignationMap.remove(Range.closed(20, 26));

        assertNull(experienceRangeDesignationMap.get(9));
        assertEquals("Managing Director", experienceRangeDesignationMap.get(16));
        assertEquals("Managing Director", experienceRangeDesignationMap.get(30));
        assertNull(experienceRangeDesignationMap.get(25));
    }

    @Test
    public void givenRangeMap_whenSpanIsCalled_returnsSucessfully() {
        final RangeMap<Integer, String> experienceRangeDesignationMap = TreeRangeMap.create();

        experienceRangeDesignationMap.put(Range.closed(0, 2), "Associate");
        experienceRangeDesignationMap.put(Range.closed(3, 5), "Senior Associate");
        experienceRangeDesignationMap.put(Range.closed(6, 8), "Vice President");
        experienceRangeDesignationMap.put(Range.closed(9, 15), "Executive Director");
        experienceRangeDesignationMap.put(Range.closed(16, 30), "Managing Director");
        final Range<Integer> experienceSpan = experienceRangeDesignationMap.span();

        assertEquals(0, experienceSpan.lowerEndpoint().intValue());
        assertEquals(30, experienceSpan.upperEndpoint().intValue());
    }

    @Test
    public void givenRangeMap_whenGetEntryIsCalled_returnsEntrySucessfully() {
        final RangeMap<Integer, String> experienceRangeDesignationMap = TreeRangeMap.create();

        experienceRangeDesignationMap.put(Range.closed(0, 2), "Associate");
        experienceRangeDesignationMap.put(Range.closed(3, 5), "Senior Associate");
        experienceRangeDesignationMap.put(Range.closed(6, 8), "Vice President");
        experienceRangeDesignationMap.put(Range.closed(9, 15), "Executive Director");
        experienceRangeDesignationMap.put(Range.closed(20, 30), "Managing Director");
        final Map.Entry<Range<Integer>, String> experiencEntry = experienceRangeDesignationMap.getEntry(10);

        assertEquals(Range.closed(9, 15), experiencEntry.getKey());
        assertEquals("Executive Director", experiencEntry.getValue());
    }

    @Test
    public void givenRangeMap_whenSubRangeMapIsCalled_returnsSubRangeSucessfully() {
        final RangeMap<Integer, String> experienceRangeDesignationMap = TreeRangeMap.create();

        experienceRangeDesignationMap.put(Range.closed(0, 2), "Associate");
        experienceRangeDesignationMap.put(Range.closed(3, 5), "Senior Associate");
        experienceRangeDesignationMap.put(Range.closed(6, 8), "Vice President");
        experienceRangeDesignationMap.put(Range.closed(8, 15), "Executive Director");
        experienceRangeDesignationMap.put(Range.closed(16, 30), "Managing Director");
        final RangeMap<Integer, String> experiencedSubRangeDesignationMap = experienceRangeDesignationMap.subRangeMap(Range.closed(4, 14));

        assertNull(experiencedSubRangeDesignationMap.get(3));
        assertTrue(experiencedSubRangeDesignationMap.asMapOfRanges().values()
            .containsAll(Arrays.asList("Executive Director", "Vice President", "Executive Director")));
        
    }

    @Test
    public void givenImmutableRangeMap_whenQueryWithinRange_returnsSucessfully() {
        final RangeMap<Integer, String> experienceRangeDesignationMap = ImmutableRangeMap.<Integer, String> builder()
	    .put(Range.closed(0, 2), "Associate")
	    .put(Range.closed(3, 5), "Senior Associate")
	    .put(Range.closed(6, 8), "Vice President")
            .put(Range.closed(9, 15), "Executive Director")
	    .put(Range.closed(16, 30), "Managing Director").build();

        assertEquals("Vice President", experienceRangeDesignationMap.get(6));
        assertEquals("Executive Director", experienceRangeDesignationMap.get(15));
    }
    
    @Test//(expected = IllegalArgumentException.class)
    public void givenImmutableRangeMap_whenRangeOverlaps_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            ImmutableRangeMap.<Integer, String> builder()
            .put(Range.closed(0, 2), "Associate")
            .put(Range.closed(3, 5), "Senior Associate")
            .put(Range.closed(6, 8), "Vice President")
                .put(Range.closed(8, 15), "Executive Director")
            .put(Range.closed(16, 30), "Managing Director").build();
        });
    }   
}
