package io.github.kavahub.learnjava.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.List;

import com.google.common.collect.Lists;

import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.util.IterableSize.*;

/**
 * 
 * {@link IterableSize} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class IterableSizeTest {
    private final List<String> list = Lists.newArrayList("Apple", "Orange", "Banana");

    @Test
    void whenUsingJava7_iterableOfCollectionType_thenCorrectSize() {

        final int size = sizeUsingJava7(list);

        assertEquals(3, size);
    }

    @Test
    void whenUsingJava7_iterableNotOfCollectionType_thenCorrect() {

        final SQLException exception = new SQLException();
        exception.setNextException(new SQLException());
        final int size = sizeUsingJava7(exception);

        assertEquals(2, size);
    }

    @Test
    void whenUsingJava8_thenCorrect() {

        final long size = sizeUsingJava8(list);

        assertEquals(3, size);
    }

    @Test
    void whenUsingApacheCollections_thenCorrect() {

        final int size = sizeUsingApacheCollections(list);

        assertEquals(3, size);
    }

    @Test
    void whenUsingGoogleGuava_thenCorrect() {

        final int size = sizeUsingGoogleGuava(list);

        assertEquals(3, size);
    }
}
