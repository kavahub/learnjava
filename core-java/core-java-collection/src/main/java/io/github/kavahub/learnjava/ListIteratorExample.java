package io.github.kavahub.learnjava;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * 
 * {@link ListIterator} 允许我们向前、向后两个方向遍历 List; 在遍历时修改 List 的元素；遍历时获取迭代器当前游标所在位置。
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ListIteratorExample {
    public static void main(String[] args) {
        List<String> items = new ArrayList<>();
        items.add("ONE");
        items.add("TWO");
        items.add("THREE");
        Iterator<String> iter = items.iterator();
        while (iter.hasNext()) {
            String next = iter.next();
            System.out.println(next);
            iter.remove();
        }

        ListIterator<String> listIterator = items.listIterator();
        while (listIterator.hasNext()) {
            // String nextWithIndex = items.get(listIterator.nextIndex());
            String next = listIterator.next();
            if ("ONE".equals(next)) {
                listIterator.set("SWAPPED");
            }
        }
        listIterator.add("FOUR");
        while (listIterator.hasPrevious()) {
            // String previousWithIndex = items.get(listIterator.previousIndex());
            String previous = listIterator.previous();
            System.out.println(previous);
        }
        listIterator.forEachRemaining(e -> {
            System.out.println(e);
        });
    }
}
