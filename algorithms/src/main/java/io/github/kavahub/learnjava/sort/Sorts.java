package io.github.kavahub.learnjava.sort;

import java.util.Comparator;

/**
 * 排序算法集成工具类，提供方便方法创建排序算法
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public interface Sorts {
    default <T> Bubble<T> bubble(Comparator<T> comparator) {
        return new Bubble<T>(comparator);
    }

    default Bucket bucket(Comparator<Integer> comparator) {
        return new Bucket(comparator);
    }
}
