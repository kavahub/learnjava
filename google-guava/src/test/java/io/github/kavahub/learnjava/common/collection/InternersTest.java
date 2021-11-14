package io.github.kavahub.learnjava.common.collection;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

import org.junit.jupiter.api.Test;

public class InternersTest {
    @Test
    public void interBuilderTest() {

        Interner<Integer> interners = Interners.newBuilder()
          .concurrencyLevel(2)
          .strong()
          .build();

        assertNotNull(interners);
    }
}
