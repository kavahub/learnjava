package io.github.kavahub.learnjava.enhance;

import java.util.Iterator;
import java.util.LinkedList;


/**
 * 
 * 字符栈，适用链表实现，非线程安全的
 *
 * @author PinWei Wan
 * @since 1.0.0
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
