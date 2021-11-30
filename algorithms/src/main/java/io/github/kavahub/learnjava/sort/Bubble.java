package io.github.kavahub.learnjava.sort;

import java.util.Comparator;
import java.util.stream.IntStream;

/**
 * 冒泡排序: 依次比较相邻的两个数，将小数放在前面，大数放在后面
 *  
 * <p>
 * 需进行n(n-1）/2次比较和记录移动， 总的时间复杂度为O(n*n)
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
public class Bubble<T> extends Sort<T> {
    public Bubble(Comparator<T> comparator) {
        super(comparator);
    }
    
    /**
     * 二重循环实现
     * 
     * @param arr
     */
    public void sort(T[] arr) {
        int n = arr.length;
        IntStream.range(0, n - 1)
          .flatMap(i -> IntStream.range(1, n - i))
          .forEach(j -> {
              if (comparator.compare(arr[j - 1], arr[j]) > 0) {
                  T temp = arr[j];
                  arr[j] = arr[j - 1];
                  arr[j - 1] = temp;
              }
          });
    }

    /**
     * 优化算法：如果数据没有交换操作，说明排序完成
     * @param arr
     */
    public void optimizedSort(T[] arr) {
        int i = 0, n = arr.length;

        // 是否交换位置
        boolean swapNeeded = true;
        while (i < n - 1 && swapNeeded) {
            swapNeeded = false;
            for (int j = 1; j < n - i; j++) {
                if (comparator.compare(arr[j - 1], arr[j]) > 0) {
                    T temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                    swapNeeded = true;
                }
            }

            // 所有数据没有交换，说明已经排序完成
            if (!swapNeeded)
                break;
            i++;
        }
    }
}
