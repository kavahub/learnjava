package io.github.kavahub.learnjava.common.collection;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.google.common.collect.MoreCollectors;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link MoreCollectors} 工具类
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class MoreCollectorsTest {

    @Test
    public void toOptionalTest() {

        List<Integer> numbers = Arrays.asList(1);

        Optional<Integer> number = numbers
          .stream()
          .map(e -> e * 2)
          .collect(MoreCollectors.toOptional());

        assertEquals(number.get(), 2);
    }

    public void testToOptionalMany() {
      assertThrows(IllegalArgumentException.class, () -> Stream.of(1, 2, 3, 4, 5, 6).collect(MoreCollectors.toOptional()));
    }

    @Test
    public void onlyElementTest() {
        List<Integer> numbers = Arrays.asList(1);

        Integer number = numbers
          .stream()
          .map(e -> e * 2)
          .collect(MoreCollectors.onlyElement());

        assertEquals(number, 2);
    }

    public void testonlyElementMany() {
      assertThrows(IllegalArgumentException.class, () -> Stream.of(1, 2, 3, 4, 5, 6).collect(MoreCollectors.onlyElement()));
    }

}
