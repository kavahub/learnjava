package io.github.kavahub.learnjava.math;

import java.util.Arrays;
import java.util.Iterator;

import org.apache.commons.math3.util.CombinatoricsUtils;

/**
 * 
 * 排列组合，使用commons库
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class CombinationUseApacheCommonsExample {
    private static final int N = 6;
    private static final int R = 3;

    /** 
     * Print all combinations of r elements from a set
     * @param n - number of elements in set
     * @param r - number of elements in selection
     */
    public static void generate(int n, int r) {
        Iterator<int[]> iterator = CombinatoricsUtils.combinationsIterator(n, r);
        while (iterator.hasNext()) {
            final int[] combination = iterator.next();
            System.out.println(Arrays.toString(combination));
        }
    }

    public static void main(String[] args) {
        generate(N, R);
    } 
}
