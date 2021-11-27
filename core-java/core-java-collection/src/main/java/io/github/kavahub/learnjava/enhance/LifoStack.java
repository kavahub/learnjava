package io.github.kavahub.learnjava.enhance;

import java.util.Collection;

/**
 * 
 * 栈，后进先出
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public interface LifoStack<E> extends Collection<E> {

    E peek();

    E pop();

    void push(E item);
}
