package io.github.kavahub.learnjava.fairnessboundedblockingqueue;

/**
 * 
 * 节点
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class Node<T> {
    public T value;
    public Node<T> next;

    public Node(T obj) {
        this.value = obj;
        next = null;
    }

    public Node() {
        this.value = null;
        next = null;
    }
}
