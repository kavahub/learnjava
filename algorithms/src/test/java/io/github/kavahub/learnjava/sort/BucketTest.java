package io.github.kavahub.learnjava.sort;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

/**
 * {@link Bucket} 测试
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
public class BucketTest {
    private final static Bucket bucket = new Bucket(Comparator.naturalOrder());

    @Test
    public void performane() {
        sort(10);
        sort(100);
        sort(1000);
        sort(10000);
        sort(100000);
        sort(1000000);
        sort(10000000);
    }

    private void sort(int size) {
        Random random = new Random();

        List<Integer> unSortList = new ArrayList<>(size);
        for(int i = 0; i < size; i++) {
            unSortList.add(random.nextInt(size));
        }

        long start = System.currentTimeMillis();
        List<Integer> sortList = bucket.sort(unSortList);
        System.out.println("Size: " + size + "\t Elapsed Time: " + (System.currentTimeMillis() - start));
        
        // 打印前100个元素
        System.out.println(sortList.stream().limit(100).collect(Collectors.toList()));
    }


    @Test
    public void givenUnsortedList_whenSortedUsingBucketSorter_checkSortingCorrect() {

        List<Integer> unsorted = Arrays.asList(80,50,60,30,20,10,70,0,40,500,600,602,200,15);
        List<Integer> expected = Arrays.asList(0,10,15,20,30,40,50,60,70,80,200,500,600,602);

        List<Integer> actual = bucket.sort(unsorted);

        assertEquals(expected, actual);

    }
}
