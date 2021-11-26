package io.github.kavahub.learnjava.enhance;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

/**
 * 后进先出
 * 
 * <p>
 * {@link ArrayDeque} 被称为“双端队列”，可以从两端进行插入或删除操作，当需要使用栈时，Java已不推荐使用Stack，
 * 而是推荐使用更高效的ArrayDeque，当需要使用队列时也可以使用ArrayDeque
 */
public class LifoStackWithArray<E> implements LifoStack<E> {
    private final Deque<E> deque = new ArrayDeque<>();

    @Override
    public void push(E item) {
        deque.addFirst(item);
    }

    @Override
    public E pop() {
        return deque.removeFirst();
    }

    @Override
    public E peek() {
        return deque.peekFirst();
    }

    // implementing methods from the Collection interface
    @Override
    public int size() {
        return deque.size();
    }

    @Override
    public boolean isEmpty() {
        return deque.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return deque.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return deque.iterator();
    }

    @Override
    public Object[] toArray() {
        return deque.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return deque.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return deque.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return deque.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return deque.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return deque.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return deque.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return deque.retainAll(c);
    }

    @Override
    public void clear() {
        deque.clear();
    }
    
}
