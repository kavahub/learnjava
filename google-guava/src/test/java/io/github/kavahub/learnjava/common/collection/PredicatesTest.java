package io.github.kavahub.learnjava.common.collection;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link Predicate} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@SuppressWarnings({"unused"})
public class PredicatesTest {
    // tests

    @Test
    public final void whenCheckingIfCollectionContainsElementsByCustomMatch1_thenContains() {
        final Iterable<String> theCollection = Lists.newArrayList("a", "bc", "def");
        final boolean contains = Iterables.any(theCollection, new Predicate<String>() {
            @Override
            public final boolean apply(final String input) {
                return input.length() == 1;
            }
        });

        assertTrue(contains);
    }

    @Test
    public final void whenCheckingIfCollectionContainsElementsByCustomMatch2_thenContains() {
        final Set<String> theCollection = Sets.newHashSet("a", "bc", "def");

        final boolean contains = !Sets.filter(theCollection, new Predicate<String>() {
            @Override
            public final boolean apply(final String input) {
                return input.length() == 1;
            }
        }).isEmpty();

        assertTrue(contains);
    }

    @Test
    public final void whenCheckingIfCollectionContainsElementsByCustomMatch3_thenContains() {
        final Iterable<String> theCollection = Sets.newHashSet("a", "bc", "def");

        final boolean contains = Iterables.find(theCollection, new Predicate<String>() {
            @Override
            public final boolean apply(final String input) {
                return input.length() == 1;
            }
        }) != null;

        assertTrue(contains);
    }

    //

    @Test//(expected = NoSuchElementException.class)
    public final void givenNoSearchResult_whenFindingElementInIterable_thenException() {
        final Iterable<String> theCollection = Sets.newHashSet("abcd", "efgh", "ijkl");

        assertThrows(NoSuchElementException.class, () -> {
            Iterables.find(theCollection, new Predicate<String>() {
                @Override
                public final boolean apply(final String input) {
                    return input.length() == 1;
                }
            });
        });

    }

    @Test
    public final void givenNoSearchResult_whenFindingElementInIterableWithSpecifiedReturn_thenNoException() {
        final Iterable<String> theCollection = Sets.newHashSet("abcd", "efgh", "ijkl");

        final Predicate<String> inputOfLengthOne = new Predicate<String>() {
            @Override
            public final boolean apply(final String input) {
                return input.length() == 1;
            }
        };
        final String found = Iterables.find(theCollection, inputOfLengthOne, null);

        assertNull(found);
    }





}
