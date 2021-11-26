package io.github.kavahub.learnjava.fairnessboundedblockingqueue;

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
