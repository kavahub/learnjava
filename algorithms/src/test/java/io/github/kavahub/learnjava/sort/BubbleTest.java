package io.github.kavahub.learnjava.sort;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Comparator;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

/**
 * {@link Bubble} 测试
 *  
 * @author
 * @since 1.0.1
 */
public class BubbleTest {
    private final static Comparator<Integer> comparator = Comparator.naturalOrder();
    private final static Bubble<Integer> bubble = new Bubble<Integer>(comparator);

    @Test
    public void performane() {
        sort(10);
        sort(100);
        sort(1000);
        sort(10000);
        //sort(50000);
        //sort(100000);
    }

    private void sort(int size) {
        Random random = new Random();

        Integer[] arr = new Integer[size];
        for(int i = 0; i < size; i++) {
            arr[i] = random.nextInt(size);
        }

        long start = System.currentTimeMillis();
        bubble.optimizedSort(arr);
        System.out.println("Size: " + size + "\t Elapsed Time: " + (System.currentTimeMillis() - start));
        
        // 打印前100个元素
        System.out.println(Stream.of(arr).limit(100).collect(Collectors.toList()));
    }

    @Test
    public void givenIntegerArray_whenSortedWithBubbleSort_thenGetSortedArray() {
        Integer[] array = { 2, 1, 4, 6, 3, 5 };
        Integer[] sortedArray = { 1, 2, 3, 4, 5, 6 };

        bubble.sort(array);
        assertArrayEquals(sortedArray, array);
    }

    @Test
    public void givenIntegerArray_whenSortedWithOptimizedBubbleSort_thenGetSortedArray() {
        Integer[] array = { 2, 1, 4, 6, 3, 5 };
        Integer[] sortedArray = { 1, 2, 3, 4, 5, 6 };

        bubble.optimizedSort(array);
        assertArrayEquals(sortedArray, array);
    }
}
