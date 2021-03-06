package io.github.kavahub.learnjava.common.collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import org.junit.jupiter.api.Test;


/**
 * 
 * {@link Multiset} 有一个有用的功能，就是跟踪每种对象的数量，所以你可以用来进行数字统计
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class MultisetTest {
    @Test
    public void givenMultiSet_whenAddingValues_shouldReturnCorrectCount() {
        Multiset<String> bookStore = HashMultiset.create();
        bookStore.add("Potter");
        bookStore.add("Potter");
        bookStore.add("Potter");

        assertThat(bookStore.contains("Potter")).isTrue();
        assertThat(bookStore.count("Potter")).isEqualTo(3);
    }

    @Test
    public void givenMultiSet_whenRemovingValues_shouldReturnCorrectCount() {
        Multiset<String> bookStore = HashMultiset.create();
        bookStore.add("Potter");
        bookStore.add("Potter");

        bookStore.remove("Potter");
        assertThat(bookStore.contains("Potter")).isTrue();
        assertThat(bookStore.count("Potter")).isEqualTo(1);
    }

    @Test
    public void givenMultiSet_whenSetCount_shouldReturnCorrectCount() {
        Multiset<String> bookStore = HashMultiset.create();
        bookStore.setCount("Potter", 50);
        assertThat(bookStore.count("Potter")).isEqualTo(50);
    }

    @Test
    public void givenMultiSet_whenSettingNegativeCount_shouldThrowException() {
        Multiset<String> bookStore = HashMultiset.create();
        assertThatThrownBy(() -> bookStore.setCount("Potter", -1))
          .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void givenMultiSet_whenSettingCountWithEmptySet_shouldBeSuccessful() {
        Multiset<String> bookStore = HashMultiset.create();
        assertThat(bookStore.setCount("Potter", 0, 2)).isTrue();
    }

    @Test
    public void givenMultiSet_whenSettingCountWithCorrectValue_shouldBeSuccessful() {
        Multiset<String> bookStore = HashMultiset.create();
        bookStore.add("Potter");
        bookStore.add("Potter");

        assertThat(bookStore.setCount("Potter", 2, 52)).isTrue();
    }

    @Test
    public void givenMultiSet_whenSettingCountWithIncorrectValue_shouldFail() {
        Multiset<String> bookStore = HashMultiset.create();
        bookStore.add("Potter");
        bookStore.add("Potter");

        assertThat(bookStore.setCount("Potter", 5, 52)).isFalse();
    }

    @Test
    public void givenMap_compareMultiSetOperations() {
        Map<String, Integer> bookStore = new HashMap<>();
        bookStore.put("Potter", 3);

        assertThat(bookStore.containsKey("Potter")).isTrue();
        assertThat(bookStore.get("Potter")).isEqualTo(3);

        bookStore.put("Potter", 2);
        assertThat(bookStore.get("Potter")).isEqualTo(2);

        bookStore.put("Potter", null);
        assertThat(bookStore.containsKey("Potter")).isTrue();

        bookStore.put("Potter", -1);
        assertThat(bookStore.containsKey("Potter")).isTrue();
    }
}
