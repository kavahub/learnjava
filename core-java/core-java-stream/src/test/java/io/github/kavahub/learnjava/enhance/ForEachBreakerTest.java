package io.github.kavahub.learnjava.enhance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class ForEachBreakerTest {
    @Test
    public void whenCustomForEachIsCalled_ThenCorrectItemsAreReturned() {
        Stream<String> initialStream = Stream.of("cat", "dog", "elephant", "fox", "rabbit", "duck");
        List<String> result = new ArrayList<>();

        ForEachBreaker.forEach(initialStream, (elem, breaker) -> {
            if (elem.length() % 2 == 0) {
                breaker.stop();
            } else {
                result.add(elem);
            }
        });

        assertEquals(Arrays.asList("cat", "dog"), result);
    }    
}
