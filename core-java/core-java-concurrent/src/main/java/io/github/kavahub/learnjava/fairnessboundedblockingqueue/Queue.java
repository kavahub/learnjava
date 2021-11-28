package io.github.kavahub.learnjava.fairnessboundedblockingqueue;

/**
 * 
 * 队列接口
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public interface Queue<T> {
    boolean offer(T value);

    T poll();

    int size();
}
