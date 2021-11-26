package io.github.kavahub.learnjava.enhance;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * {@link LinkedList} 是基于链表实现。相应地，也就是说在新增、删除元素时，LinkedList 的效率要高
 */
public class CharStackWithLinkedList {
    private LinkedList<Character> items;

    public CharStackWithLinkedList() {
        this.items = new LinkedList<Character>();
    }

    public void push(Character item) {
        items.push(item);
    }

    public Character peek() {
        return items.getFirst();
    }

    public Character pop() {
        Iterator<Character> iter = items.iterator();
        Character item = iter.next();
        if (item != null) {
            iter.remove();
            return item;
        }
        return null;
    }

    public int size() {
        return items.size();
    }
}
