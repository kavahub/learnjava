package io.github.kavahub.learnjava.common.collection;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Function;
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
 * 手工测试
 *  
 * @author PinWei Wan
 * @since 1.0.0
 */
@SuppressWarnings({"unused", "unchecked"})
public class ManualTest {
        // purge of nulls

        @Test
        public final void givenListContainsNulls_whenPurgedOfNulls_thenNoLongerContainsNulls() {
            final List<String> values = Lists.newArrayList("a", null, "b", "c");
            final Iterable<String> withoutNulls = Iterables.filter(values, Predicates.notNull());
            System.out.println(withoutNulls);
        }
    
        // immutable collections
    
        @Test
        public final void whenCreatingImuutableCollections_thenNoExceptions() {
            final ImmutableList<String> immutableList = ImmutableList.of("a", "b", "c");
            final ImmutableSet<String> immutableSet = ImmutableSet.of("a", "b", "c");
            final ImmutableMap<String, String> imuttableMap = ImmutableMap.of("k1", "v1", "k2", "v2", "k3", "v3");
        }
    
        @Test
        public final void whenTransformingCollectionsToImmutable_thenNoExceptions() {
            final List<String> muttableList = Lists.newArrayList();
            final ImmutableList<String> immutableList = ImmutableList.copyOf(muttableList);
    
            final Set<String> muttableSet = Sets.newHashSet();
            final ImmutableSet<String> immutableSet = ImmutableSet.copyOf(muttableSet);
    
            final Map<String, String> muttableMap = Maps.newHashMap();
            final ImmutableMap<String, String> imuttableMap = ImmutableMap.copyOf(muttableMap);
        }
    
        @Test
        public final void whenTransformingCollectionsToImmutableViaBuilders_thenNoExceptions() {
            final List<String> muttableList = Lists.newArrayList();
            final ImmutableList<String> immutableList = ImmutableList.<String> builder().addAll(muttableList).build();
    
            final Set<String> muttableSet = Sets.newHashSet();
            final ImmutableSet<String> immutableSet = ImmutableSet.<String> builder().addAll(muttableSet).build();
    
            final Map<String, String> muttableMap = Maps.newHashMap();
            final ImmutableMap<String, String> imuttableMap = ImmutableMap.<String, String> builder().putAll(muttableMap).build();
        }

        @Test
        public final void whenDowncastingGenerifiedCollectionToNewGenerifiedCollection_thenCastIsOK() {
            final class CastFunction<F, T extends F> implements Function<F, T> {
                @Override
                public final T apply(final F from) {
                    return (T) from;
                }
            }
    
            final List<Number> originalList = Lists.newArrayList();
            final List<Integer> selectedProducts = Lists.transform(originalList, new CastFunction<Number, Integer>());
            System.out.println(selectedProducts);
        }
    
        @Test
        public final void whenDowncastingGenerifiedCollectionToNewGenerifiedCollection_thenCastIsOK2() {
            final List<Number> originalList = Lists.newArrayList();
            final List<Integer> selectedProducts = (List<Integer>) (List<? extends Number>) originalList;
            System.out.println(selectedProducts);
        }
    
        @Test
        public final void whenAddingAnIterableToACollection_thenAddedOK() {
            final Iterable<String> iter = Lists.newArrayList();
            final Collection<String> collector = Lists.newArrayList();
            Iterables.addAll(collector, iter);
        }
    
        //        
}
