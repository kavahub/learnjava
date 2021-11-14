package io.github.kavahub.learnjava.common.collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.junit.jupiter.api.Test;

public class CollectionPartitionGuavaTest {
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
