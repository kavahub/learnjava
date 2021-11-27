package io.github.kavahub.learnjava.common.collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.apache.commons.collections4.ListUtils;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * 分割集合集合
 * 
 * @see Lists#partition(List, int)
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class CollectionPartitionTest {
    // tests - apache common collections
    @Nested
    public static class ApacheTest {
        @Test
        public final void givenList_whenParitioningIntoNSublists_thenCorrect() {
            final List<Integer> intList = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8);

            final List<List<Integer>> subSets = ListUtils.partition(intList, 3);

            // When
            final List<Integer> lastPartition = subSets.get(2);
            final List<Integer> expectedLastPartition = Lists.<Integer>newArrayList(7, 8);
            assertThat(subSets.size(), equalTo(3));
            assertThat(lastPartition, equalTo(expectedLastPartition));
        }

        @Test
        public final void givenListPartitioned_whenOriginalListIsModified_thenPartitionsChange() {
            // Given
            final List<Integer> intList = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8);
            final List<List<Integer>> subSets = ListUtils.partition(intList, 3);

            // When
            intList.add(9);
            final List<Integer> lastPartition = subSets.get(2);
            final List<Integer> expectedLastPartition = Lists.<Integer>newArrayList(7, 8, 9);
            assertThat(lastPartition, equalTo(expectedLastPartition));
        }
    }

    @Nested
    public static class JavaTest {
        // java8 groupBy
        @Test
        public final void givenList_whenParitioningIntoNSublistsUsingGroupingBy_thenCorrect() {
            final List<Integer> intList = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8);
    
            final Map<Integer, List<Integer>> groups = intList.stream().collect(Collectors.groupingBy(s -> (s - 1) / 3));
            final List<List<Integer>> subSets = new ArrayList<List<Integer>>(groups.values());
    
            // When
            final List<Integer> lastPartition = subSets.get(2);
            final List<Integer> expectedLastPartition = Lists.<Integer>newArrayList(7, 8);
            assertThat(subSets.size(), equalTo(3));
            assertThat(lastPartition, equalTo(expectedLastPartition));
    
            // intList.add(9);
            // System.out.println(groups.values());
        }
    
        // java8 partitionBy
        @Test
        public final void givenList_whenParitioningIntoSublistsUsingPartitionBy_thenCorrect() {
            final List<Integer> intList = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8);
    
            final Map<Boolean, List<Integer>> groups = intList.stream().collect(Collectors.partitioningBy(s -> s > 6));
            final List<List<Integer>> subSets = new ArrayList<List<Integer>>(groups.values());
    
            // When
            final List<Integer> lastPartition = subSets.get(1);
            final List<Integer> expectedLastPartition = Lists.<Integer>newArrayList(7, 8);
            assertThat(subSets.size(), equalTo(2));
            assertThat(lastPartition, equalTo(expectedLastPartition));
    
            // intList.add(9);
            // System.out.println(groups.values());
        }
    
        // java8 split by separator
        @Test
        public final void givenList_whenSplittingBySeparator_thenCorrect() {
            final List<Integer> intList = Lists.newArrayList(1, 2, 3, 0, 4, 5, 6, 0, 7, 8);
    
            final int[] indexes = Stream.of(IntStream.of(-1),
                    IntStream.range(0, intList.size()).filter(i -> intList.get(i) == 0), IntStream.of(intList.size()))
                    .flatMapToInt(s -> s).toArray();
            final List<List<Integer>> subSets = IntStream.range(0, indexes.length - 1)
                    .mapToObj(i -> intList.subList(indexes[i] + 1, indexes[i + 1])).collect(Collectors.toList());
    
            // When
            final List<Integer> lastPartition = subSets.get(2);
            final List<Integer> expectedLastPartition = Lists.<Integer>newArrayList(7, 8);
            assertThat(subSets.size(), equalTo(3));
            assertThat(lastPartition, equalTo(expectedLastPartition));
        }
    
    }
    
    @Nested
    public static class GuavaTest {
        // tests - guava
    
        @Test
        public final void givenList_whenParitioningIntoNSublists_thenCorrect() {
            final List<Integer> intList = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8);
    
            final List<List<Integer>> subSets = Lists.partition(intList, 3);
    
            assertThat(subSets.size(), equalTo(3));
    
            // When
            final List<Integer> lastPartition = subSets.get(2);
            final List<Integer> expectedLastPartition = Lists.<Integer> newArrayList(7, 8);
            assertThat(lastPartition, equalTo(expectedLastPartition));
        }
    
        @Test
        public final void givenListPartitioned_whenOriginalListIsModified_thenPartitionsChangeAsWell() {
            // Given
            final List<Integer> intList = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8);
            final List<List<Integer>> subSets = Lists.partition(intList, 3);
    
            // When
            intList.add(9);
            final List<Integer> lastPartition = subSets.get(2);
            final List<Integer> expectedLastPartition = Lists.<Integer> newArrayList(7, 8, 9);
            assertThat(lastPartition, equalTo(expectedLastPartition));
        }
    
        @Test
        public final void givenCollection_whenParitioningIntoNSublists_thenCorrect() {
            final Collection<Integer> intCollection = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8);
    
            final Iterable<List<Integer>> subSets = Iterables.partition(intCollection, 3);
    
            // When
            final List<Integer> firstPartition = subSets.iterator().next();
            final List<Integer> expectedLastPartition = Lists.<Integer> newArrayList(1, 2, 3);
            assertThat(firstPartition, equalTo(expectedLastPartition));
        }
    
    }
    
}
