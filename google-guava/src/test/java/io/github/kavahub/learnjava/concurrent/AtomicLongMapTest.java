package io.github.kavahub.learnjava.concurrent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.common.util.concurrent.AtomicLongMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 * {@link AtomicLongMap} 实现多线程环境下计数
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class AtomicLongMapTest {
    private static final String SPRING_COURSE_KEY = "Spring";
    private static final String HIBERNATE_COURSE_KEY = "hibernate";
    private static final String GUAVA_COURSE_KEY = "Guava";

    AtomicLongMap<String> courses = AtomicLongMap.create();

    @BeforeEach
    public void setUp() {
        courses.clear();
        courses.put(SPRING_COURSE_KEY, 1056);
        courses.put(HIBERNATE_COURSE_KEY, 259);
        courses.put(GUAVA_COURSE_KEY, 78);
    }

    @Test
    public void accumulateAndGet_withLongBinaryOperator_thenSuccessful() {
        long noOfStudents = 56;
        long oldValue = courses.get(SPRING_COURSE_KEY);

        //System.out.println(courses);
        //long totalNotesRequired = courses.accumulateAndGet("Guava", noOfStudents, (x, y) -> (x * y));
        long totalNotesRequired = courses.accumulateAndGet(SPRING_COURSE_KEY, noOfStudents, (x, y) -> (x * y));
        //System.out.println(courses);
        assertEquals(totalNotesRequired, oldValue * noOfStudents);
    }

    @Test
    public void getAndAccumulate_withLongBinaryOperator_thenSuccessful() {
        long noOfStudents = 56;
        long beforeUpdate = courses.get(SPRING_COURSE_KEY);

        //long onUpdate = courses.accumulateAndGet("Guava", noOfStudents, (x, y) -> (x * y));
        long onUpdate = courses.accumulateAndGet(SPRING_COURSE_KEY, noOfStudents, (x, y) -> (x * y));

        long afterUpdate = courses.get(SPRING_COURSE_KEY);

        assertEquals(onUpdate, afterUpdate);
        assertEquals(afterUpdate, beforeUpdate * noOfStudents);
    }

    @Test
    public void updateAndGet_withLongUnaryOperator_thenSuccessful() {
        long beforeUpdate = courses.get(SPRING_COURSE_KEY);

        //long onUpdate = courses.updateAndGet("Guava", (x) -> (x / 2));
        long onUpdate = courses.updateAndGet(SPRING_COURSE_KEY, (x) -> (x / 2));

        long afterUpdate = courses.get(SPRING_COURSE_KEY);

        assertEquals(onUpdate, afterUpdate);
        assertEquals(afterUpdate, beforeUpdate / 2);
    }
}
