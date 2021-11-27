package io.github.kavahub.learnjava;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link Collections} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class CollectionMethodTest {
    @Test
    public void givenCollection_whenUsingRemoveIfToRemoveAllNamesStartingWithLetterA_finalListShouldContainNoNamesStartingWithLetterA() {
        List<String> names = new ArrayList<>();

        names.add("John");
        names.add("Ana");
        names.add("Mary");
        names.add("Anthony");
        names.add("Mark");
        
        names.removeIf(e -> e.startsWith("A"));
        assertThat(names.size(), is(3));
        assertThat(names.get(1), is("Mary"));
    }


    @Test
    public void whenShufflingList_thenListIsShuffled() {
        List<String> students = Arrays.asList("Foo", "Bar", "Baz", "Qux");

        System.out.println("List before shuffling:");
        System.out.println(students);

        Collections.shuffle(students);
        System.out.println("List after shuffling:");
        System.out.println(students);
    }

    @Test
    public void whenShufflingMapEntries_thenValuesAreShuffled() {
        Map<Integer, String> studentsById = new HashMap<>();
        studentsById.put(1, "Foo");
        studentsById.put(2, "Bar");
        studentsById.put(3, "Baz");
        studentsById.put(4, "Qux");

        System.out.println("Students before shuffling:");
        System.out.println(studentsById.values());

        List<Map.Entry<Integer, String>> shuffledStudentEntries = new ArrayList<>(studentsById.entrySet());
        Collections.shuffle(shuffledStudentEntries);

        List<String> shuffledStudents = shuffledStudentEntries.stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        System.out.println("Students after shuffling");
        System.out.println(shuffledStudents);
    }

    @Test
    public void whenShufflingSet_thenElementsAreShuffled() {
        Set<String> students = new HashSet<>(Arrays.asList("Foo", "Bar", "Baz", "Qux"));

        System.out.println("Set before shuffling:");
        System.out.println(students);

        List<String> studentList = new ArrayList<>(students);

        Collections.shuffle(studentList);
        System.out.println("Shuffled set elements:");
        System.out.println(studentList);
    }

    @Test
    public void whenShufflingWithSameRandomness_thenElementsAreShuffledDeterministically() {
        List<String> students_1 = Arrays.asList("Foo", "Bar", "Baz", "Qux");
        List<String> students_2 = Arrays.asList("Foo", "Bar", "Baz", "Qux");

        Collections.shuffle(students_1, new Random(5));
        Collections.shuffle(students_2, new Random(5));

        assertThat(students_1).isEqualTo(students_2);
    }
}
