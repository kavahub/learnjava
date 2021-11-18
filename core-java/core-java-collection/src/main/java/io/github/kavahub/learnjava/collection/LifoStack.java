package io.github.kavahub.learnjava.collection;

import java.util.Collection;

/**
 * 后进先出
 */
public interface LifoStack<E> extends Collection<E> {

    E peek();

    E pop();

    void push(E item);
}
