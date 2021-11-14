package io.github.kavahub.learnjava.common.collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import com.google.common.collect.Lists;

import org.apache.commons.collections4.ListUtils;
import org.junit.jupiter.api.Test;

public class CollectionPartitionApacheTest {
    // tests - apache common collections

    @Test
    public final void givenList_whenParitioningIntoNSublists_thenCorrect() {
        final List<Integer> intList = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8);

        final List<List<Integer>> subSets = ListUtils.partition(intList, 3);

        // When
        final List<Integer> lastPartition = subSets.get(2);
        final List<Integer> expectedLastPartition = Lists.<Integer> newArrayList(7, 8);
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
        final List<Integer> expectedLastPartition = Lists.<Integer> newArrayList(7, 8, 9);
        assertThat(lastPartition, equalTo(expectedLastPartition));
    }

}
