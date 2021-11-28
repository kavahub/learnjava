package io.github.kavahub.learnjava.math;

import java.util.Arrays;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

/**
 * 
 * 排列组合，使用Guava库
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class CombinationUseGuavaExample {
    public static void main(String[] args) {

        Set<Set<Integer>> combinations = Sets.combinations(ImmutableSet.of(0, 1, 2, 3, 4, 5), 3);
        System.out.println(combinations.size());
        System.out.println(Arrays.toString(combinations.toArray()));
    }   
}
