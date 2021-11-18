package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FindInListTest {
    private final static int SIZE = 100;
    private final static List<Integer> list = new ArrayList<>();

    @BeforeAll
    public static void beforeAll() {
        for(int i = 0; i < SIZE; i++) {
            list.add(i);
        }
    }

    @Test
    public void givenIndex_whenFind() {
        final int givenIndex = ThreadLocalRandom.current().nextInt(SIZE);
        assertNotNull(list.get(givenIndex));
    }

    @Test
    public void givenObject_whenFindByIndexOf() {
        final Integer givenObject = Integer.valueOf(ThreadLocalRandom.current().nextInt(SIZE));
        assertNotNull(list.indexOf(givenObject));
    }

    @Test
    public void givenObject_whenFindByIterator() {
        final Integer givenObject = Integer.valueOf(ThreadLocalRandom.current().nextInt(SIZE));


        Integer actualObject = null;
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            actualObject = iterator.next();
            if (givenObject.equals(actualObject)) {
                break;
            }
        }

        assertEquals(givenObject, actualObject);
    }

    @Test
    public void givenObject_whenFindByLoop() {
        final Integer givenObject = Integer.valueOf(ThreadLocalRandom.current().nextInt(SIZE));


        Integer actualObject = null;
        for(Integer integer : list) {
            if (integer.equals(givenObject)) {
                actualObject = integer;
            }
        }

        assertEquals(givenObject, actualObject);
    }

    @Test
    public void givenObject_whenFindByStream() {
        final Integer givenObject = Integer.valueOf(ThreadLocalRandom.current().nextInt(SIZE));

        Integer actualObject = list.stream().filter(givenObject::equals).findFirst().orElse(null);

        assertEquals(givenObject, actualObject);
    }

    @Test
    public void givenObject_whenFindByParallelStream() {
        final Integer givenObject = Integer.valueOf(ThreadLocalRandom.current().nextInt(SIZE));

        Integer actualObject = list.parallelStream().filter(givenObject::equals).findFirst().orElse(null);

        assertEquals(givenObject, actualObject);
    }

    @Test
    public void givenObject_whenFindByGuava() {
        final Integer givenObject = Integer.valueOf(ThreadLocalRandom.current().nextInt(SIZE));

        Integer actualObject =  Iterables.tryFind(list, new Predicate<Integer>() {
            public boolean apply(Integer o) {
                return o.equals(givenObject);
            }
        }).orNull();

        assertEquals(givenObject, actualObject);
    }

    @Test
    public void givenObject_whenFindByApacheCommon() {
        final Integer givenObject = Integer.valueOf(ThreadLocalRandom.current().nextInt(SIZE));

        Integer actualObject =  IterableUtils.find(list, new org.apache.commons.collections4.Predicate<Integer>() {
            public boolean evaluate(Integer o) {
                return o.equals(givenObject);
            }
        });

        assertEquals(givenObject, actualObject);
    }

}
