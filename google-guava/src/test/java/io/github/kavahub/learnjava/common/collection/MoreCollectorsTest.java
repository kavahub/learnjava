package io.github.kavahub.learnjava.common.collection;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.MoreCollectors;

import org.junit.jupiter.api.Test;

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

    private void assertEquals(Integer integer, int i) {
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
}
