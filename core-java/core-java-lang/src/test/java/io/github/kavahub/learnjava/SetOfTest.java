package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * {@link Set#of()} 方法及其重载方法使用示例
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class SetOfTest {
    @Test
    public void whenSetCreated_thenSuccess() {
        Set<String> traditionlSet = new HashSet<String>();
        traditionlSet.add("foo");
        traditionlSet.add("bar");
        traditionlSet.add("baz");
        Set<String> factoryCreatedSet = Set.of("foo", "bar", "baz");
        assertEquals(traditionlSet, factoryCreatedSet);
    }

    @Test
    public void onDuplicateElem_IfIllegalArgExp_thenSuccess() {
        assertThrows(IllegalArgumentException.class, () -> Set.of("foo", "bar", "baz", "foo"));
    }

    @Test
    public void onElemAdd_ifUnSupportedOpExpnThrown_thenSuccess() {
        Set<String> set = Set.of("foo", "bar");
        assertThrows(UnsupportedOperationException.class, () -> set.add("baz"));
    }

    @Test
    public void onElemRemove_ifUnSupportedOpExpnThrown_thenSuccess() {
        Set<String> set = Set.of("foo", "bar", "baz");
        assertThrows(UnsupportedOperationException.class, () -> set.remove("foo"));
    }

    @Test
    public void onNullElem_ifNullPtrExpnThrown_thenSuccess() {
        assertThrows(NullPointerException.class, () -> Set.of("foo", "bar", null));
    }

    @Test
    public void ifNotHashSet_thenSuccess() {
        Set<String> list = Set.of("foo", "bar");
        assertFalse(list instanceof HashSet);
    }

    @Test
    public void ifSetSizeIsOne_thenSuccess() {
        int[] arr = { 1, 2, 3, 4 };
        Set<int[]> set = Set.of(arr);
        assertEquals(1, set.size());
        assertArrayEquals(arr, set.iterator().next());
    }   
}
