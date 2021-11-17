package io.github.kavahub.learnjava.collection;

import java.util.Iterator;
import java.util.LinkedList;

public class CharStack {
    // LinkedList类是双向列表,列表中的每个节点都包含了对前一个和后一个元素的引用.
    private LinkedList<Character> items;

    public CharStack() {
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
