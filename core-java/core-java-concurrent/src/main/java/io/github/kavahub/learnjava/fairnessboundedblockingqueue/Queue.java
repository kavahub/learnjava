package io.github.kavahub.learnjava.fairnessboundedblockingqueue;

public interface Queue<T> {
    boolean offer(T value);

    T poll();

    int size();
}
