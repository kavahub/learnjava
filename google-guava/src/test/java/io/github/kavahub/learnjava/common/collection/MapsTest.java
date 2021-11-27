package io.github.kavahub.learnjava.common.collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.MutableClassToInstanceMap;
import com.google.common.collect.Ordering;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link ImmutableMap} 用于创建不可变Map实例，常用于创建一个对象常量映射，来保存一些常量映射的键值对
 *
 * <p>
 * {@link ImmutableSortedMap} 用法与
 * <code>ImmutableMap</code> 类似，区别是包含的元素是排序的，并且提供许多查找方法
 * 
 * <p>
 * {@link HashBiMap} 是 <code>BiMap</code> 接口实现，存储的键和值都只能唯一，不存在键与键、值与值相同的情况
 * 
 * <p>
 * {@link BiMap} 接口继承了 <code>Map</code> ，特点是它的 value 和它 key 一样也是不可重复的，换句话说它的 key
 * 和 value 是等价的，所以提供了key和value的双向关联。如果放了重复的元素，就会得到 <code>IllegalArgumentException</code> 异常
 * 
 * <p>
 * {@link ArrayListMultimap} 是 <code>Multimap</code> 接口实现，一键多值的集合
 * 
 * <p>
 * {@link Multimap} 是接口, 并 <strong>没有</strong> 继承 <code>Map</code> (可以找到规律，<code>Multimap</code>的m是小写)。提供了一个方便地把一个键对应到多个值的数据结构
 * 
 * <p>
 * {@link Multimaps} 工具类，提供方便方法创建 <code>Multimap</code> 实例
 * 
 * <p>
 * {@link HashBasedTable} 实现了 <code>Table</code> 接口
 * 
 * <p>
 * {@link Table} 接口，提供多个索引的数据结构，有以下实现：
 * <ul>
 * <li> HashBasedTable：基于HashMap<R, HashMap<C, V>>的实现</li>
 * <li> TreeBasedTable：基于TreeMap<R, TreeMap<C, V>>的实现。</li>
 * <li> ImmutableTable：基于ImmutableMap<R, ImmutableMap<C, V>>的实现</li>
 * <li> ArrayTable：ArrayTable是一个需要在构建的时候就需要定下行列的表格。这种表格由二维数组实现</li>
 * </ul>
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class MapsTest {
    @Test
    public void whenCreateImmutableMap_thenCreated() {
        final Map<String, Integer> salary = ImmutableMap.<String, Integer>builder().put("John", 1000).put("Jane", 1500)
                .put("Adam", 2000).put("Tom", 2000).build();

        assertEquals(1000, salary.get("John").intValue());
        assertEquals(2000, salary.get("Tom").intValue());
    }

    @Test
    public void whenUseSortedMap_thenKeysAreSorted() {
        final ImmutableSortedMap<String, Integer> salary = new ImmutableSortedMap.Builder<String, Integer>(
                Ordering.natural()).put("John", 1000).put("Jane", 1500).put("Adam", 2000).put("Tom", 2000).build();

        assertEquals("Adam", salary.firstKey());
        assertEquals(2000, salary.lastEntry().getValue().intValue());
    }

    @Test
    public void whenCreateBiMap_thenCreated() {
        final BiMap<String, Integer> words = HashBiMap.create();
        words.put("First", 1);
        words.put("Second", 2);
        words.put("Third", 3);

        assertEquals(2, words.get("Second").intValue());
        assertEquals("Third", words.inverse().get(3));
    }

    @Test
    public void whenCreateMultimap_thenCreated() {
        final Multimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("fruit", "apple");
        multimap.put("fruit", "banana");
        multimap.put("pet", "cat");
        multimap.put("pet", "dog");

        assertThat(multimap.get("fruit"), containsInAnyOrder("apple", "banana"));
        assertThat(multimap.get("pet"), containsInAnyOrder("cat", "dog"));
    }

    @Test
    public void whenGroupListUsingMultimap_thenGrouped() {
        final List<String> names = Lists.newArrayList("John", "Adam", "Tom");
        final Function<String, Integer> function = new Function<String, Integer>() {
            @Override
            public final Integer apply(final String input) {
                return input.length();
            }
        };
        final Multimap<Integer, String> groups = Multimaps.index(names, function);

        assertThat(groups.get(3), containsInAnyOrder("Tom"));
        assertThat(groups.get(4), containsInAnyOrder("John", "Adam"));
    }

    @Test
    public void whenCreateTable_thenCreated() {
        final Table<String, String, Integer> distance = HashBasedTable.create();
        distance.put("London", "Paris", 340);
        distance.put("New York", "Los Angeles", 3940);
        distance.put("London", "New York", 5576);

        assertEquals(3940, distance.get("New York", "Los Angeles").intValue());
        assertThat(distance.columnKeySet(), containsInAnyOrder("Paris", "New York", "Los Angeles"));
        assertThat(distance.rowKeySet(), containsInAnyOrder("London", "New York"));
    }

    @Test
    public void whenTransposeTable_thenCorrect() {
        final Table<String, String, Integer> distance = HashBasedTable.create();
        distance.put("London", "Paris", 340);
        distance.put("New York", "Los Angeles", 3940);
        distance.put("London", "New York", 5576);

        final Table<String, String, Integer> transposed = Tables.transpose(distance);
        assertThat(transposed.rowKeySet(), containsInAnyOrder("Paris", "New York", "Los Angeles"));
        assertThat(transposed.columnKeySet(), containsInAnyOrder("London", "New York"));
    }

    @Test
    public void whenCreateClassToInstanceMap_thenCreated() {
        final ClassToInstanceMap<Number> numbers = MutableClassToInstanceMap.create();
        numbers.putInstance(Integer.class, 1);
        numbers.putInstance(Double.class, 1.5);

        assertEquals(1, numbers.get(Integer.class));
        assertEquals(1.5, numbers.get(Double.class));
    }

}
