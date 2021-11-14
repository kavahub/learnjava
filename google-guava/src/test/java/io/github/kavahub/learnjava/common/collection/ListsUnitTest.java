package io.github.kavahub.learnjava.common.collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.junit.jupiter.api.Test;

public class ListsUnitTest {
    @Test
    public void whenCreateList_thenCreated() {
        final List<String> names = Lists.newArrayList("John", "Adam", "Jane");

        names.add("Tom");
        assertEquals(4, names.size());

        names.remove("Adam");
        assertThat(names, contains("John", "Jane", "Tom"));
    }

    @Test
    public void whenReverseList_thenReversed() {
        final List<String> names = Lists.newArrayList("John", "Adam", "Jane");

        final List<String> reversed = Lists.reverse(names);
        assertThat(reversed, contains("Jane", "Adam", "John"));
    }

    @Test
    public void whenCreateCharacterListFromString_thenCreated() {
        final List<Character> chars = Lists.charactersOf("John");

        assertEquals(4, chars.size());
        assertThat(chars, contains('J', 'o', 'h', 'n'));
    }

    @Test
    public void whenPartitionList_thenPartitioned() {
        final List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom", "Viki", "Tyler");
        final List<List<String>> result = Lists.partition(names, 2);

        assertEquals(3, result.size());
        assertThat(result.get(0), contains("John", "Jane"));
        assertThat(result.get(1), contains("Adam", "Tom"));
        assertThat(result.get(2), contains("Viki", "Tyler"));
    }

    @Test
    public void whenRemoveDuplicatesFromList_thenRemoved() {
        final List<Character> chars = Lists.newArrayList('h', 'e', 'l', 'l', 'o');
        assertEquals(5, chars.size());

        final List<Character> result = ImmutableSet.copyOf(chars).asList();
        assertThat(result, contains('h', 'e', 'l', 'o'));
    }

    @Test
    public void whenRemoveNullFromList_thenRemoved() {
        final List<String> names = Lists.newArrayList("John", null, "Adam", null, "Jane");
        Iterables.removeIf(names, Predicates.isNull());

        assertEquals(3, names.size());
        assertThat(names, contains("John", "Adam", "Jane"));
    }

    @Test
    public void whenCreateImmutableList_thenCreated() {
        final List<String> names = Lists.newArrayList("John", "Adam", "Jane");

        names.add("Tom");
        assertEquals(4, names.size());

        final ImmutableList<String> immutable = ImmutableList.copyOf(names);
        assertThat(immutable, contains("John", "Adam", "Jane", "Tom"));
    }   
}
