package io.github.kavahub.learnjava.sort;

import java.util.Comparator;

/**
 * 排序基础类
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public abstract class Sort<T> {
    protected Comparator<T> comparator;

    public Sort(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public Sort() {
    }

    
}
