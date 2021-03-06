

package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link List} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ListFactoryMethodsTest {
    @Test
    public void whenListCreated_thenSuccess() {
        List<String> traditionlList = new ArrayList<String>();
        traditionlList.add("foo");
        traditionlList.add("bar");
        traditionlList.add("baz");
        List<String> factoryCreatedList = List.of("foo", "bar", "baz");
        assertEquals(traditionlList, factoryCreatedList);
    }

    @Test
    public void onElemAdd_ifUnSupportedOpExpnThrown_thenSuccess() {
        List<String> list = List.of("foo", "bar");
        assertThrows(UnsupportedOperationException.class, () -> list.add("baz"));
    }

    @Test
    public void onElemModify_ifUnSupportedOpExpnThrown_thenSuccess() {
        List<String> list = List.of("foo", "bar");
        assertThrows(UnsupportedOperationException.class, () -> list.set(0, "baz"));
    }

    @Test
    public void onElemRemove_ifUnSupportedOpExpnThrown_thenSuccess() {
        List<String> list = List.of("foo", "bar");
        assertThrows(UnsupportedOperationException.class, () -> list.remove("foo"));
    }

    @Test
    public void onNullElem_ifNullPtrExpnThrown_thenSuccess() {
        assertThrows(NullPointerException.class, () -> List.of("foo", "bar", null));
    }

    @Test
    public void ifNotArrayList_thenSuccess() {
        List<String> list = List.of("foo", "bar");
        assertFalse(list instanceof ArrayList);
    }

    @Test
    public void ifListSizeIsOne_thenSuccess() {
        int[] arr = { 1, 2, 3, 4 };
        List<int[]> list = List.of(arr);
        assertEquals(1, list.size());
        assertArrayEquals(arr, list.get(0));
    }   
}
