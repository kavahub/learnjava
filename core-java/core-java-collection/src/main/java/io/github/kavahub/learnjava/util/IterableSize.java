package io.github.kavahub.learnjava.util;

import java.util.Collection;
import java.util.stream.StreamSupport;

import com.google.common.collect.Iterables;

import org.apache.commons.collections4.IterableUtils;

import lombok.experimental.UtilityClass;

/**
 * 
 * 获取迭代器大小
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class IterableSize {
  /**
     * Get the size of {@code Iterable} using Java 7.
     *
     * @param data the iterable
     * @return the size of the iterable
     */
    @SuppressWarnings("unused")
    public static int sizeUsingJava7(final Iterable<?> data) {

        if (data instanceof Collection) {
            return ((Collection<?>) data).size();
        }
        int counter = 0;

        
        for (final Object i : data) {
            counter++;
        }
        return counter;
    }

    /**
     * Get the size of {@code Iterable} using Java 8.
     *
     * @param data the iterable
     * @return the size of the iterable
     */
    public static long sizeUsingJava8(final Iterable<?> data) {

        return StreamSupport.stream(data.spliterator(), false).count();
    }

    /**
     * Get the size of {@code Iterable} using Apache Collections.
     *
     * @param data the iterable
     * @return the size of the iterable
     */
    public static int sizeUsingApacheCollections(final Iterable<?> data) {

        return IterableUtils.size(data);
    }

    /**
     * Get the size of {@code Iterable} using Google Guava.
     *
     * @param data the iterable
     * @return the size of the iterable
     */
    public static int sizeUsingGoogleGuava(final Iterable<?> data) {

        return Iterables.size(data);
    }  
}
