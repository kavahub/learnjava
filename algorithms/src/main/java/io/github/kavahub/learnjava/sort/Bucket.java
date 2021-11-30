package io.github.kavahub.learnjava.sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 桶排序算法
 * 
 * <p>
 * 桶排序以下列程序进行：
 * <ul>
 * <li> 设置一个定量的数组当作空桶子 </li>
 * <li> 寻访序列，并且把项目一个一个放到对应的桶子去 </li>
 * <li> 对每个不是空的桶子进行排序 </li>
 * <li> 从不是空的桶子里把项目再放回原来的序列中 </li>
 * </ul>
 * 
 * <p>
 * 关键算法：
 * <ul>
 * <li> 桶数量(numberOfBuckets) : 排序数组大小的开方 </li>
 * <li> 最大值(maxValue) : 排序数组中的最大值 </li>
 * <li> 求Hash值(也就是放入哪个桶中) : 当前排序值/maxValue * (numberOfBuckets - 1) </li>
 * </ul>
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
public class Bucket extends Sort<Integer> {

    public Bucket(Comparator<Integer> comparator) {
        super(comparator);
    }

    public List<Integer> sort(List<Integer> unsortList) {
        // 分桶
        List<List<Integer>> buckets = splitIntoBuckets(unsortList);
        sortAsync(buckets);

        return buckets.stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    /**
     * 异步排序每个桶
     * 
     * @param buckets
     */
    private void sortAsync(List<List<Integer>> buckets) {
        CompletableFuture<?>[] tasks = buckets.stream()
                .map(bucket -> CompletableFuture.runAsync(() -> bucket.sort(comparator)))
                .toArray(CompletableFuture<?>[]::new);

        CompletableFuture.allOf(tasks).join();
    }

    /**
     * 分桶算法
     * 
     * @param list
     * @return
     */
    private List<List<Integer>> splitIntoBuckets(List<Integer> list) {

        final int maxValue = findMax(list);
        // 求开方
        final int numberOfBuckets = (int) Math.sqrt(list.size());

        List<List<Integer>> buckets = new ArrayList<>();
        for (int i = 0; i < numberOfBuckets; i++)
            buckets.add(new ArrayList<>());

        // distribute the data
        for (int i : list) {
            final int hash = hash(i, maxValue, numberOfBuckets);
            buckets.get(hash).add(i);
        }
        return buckets;

    }

    private int findMax(List<Integer> input) {
        return input.stream()
                // 找最大值
                .max(Comparator.naturalOrder()).get();
    }

    /**
     * 算法核心
     * 
     * @param i               当前数
     * @param max             最大数
     * @param numberOfBuckets 桶数
     * @return
     */
    private static int hash(int i, int max, int numberOfBuckets) {
        double hash = (double) i / max * (numberOfBuckets - 1);
        //System.out.println(i + " : " + hash);
        return (int) hash;
    }
}
