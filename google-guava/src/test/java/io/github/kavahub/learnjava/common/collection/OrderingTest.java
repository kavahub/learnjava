package io.github.kavahub.learnjava.common.collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link Ordering} 示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class OrderingTest {
    @Test
    public void givenListOfIntegers_whenCreateNaturalOrderOrdering_shouldSortProperly() {
        //given
        List<Integer> integers = Arrays.asList(3, 2, 1);

        //when
        integers.sort(Ordering.natural());

        //then
        assertEquals(Arrays.asList(1, 2, 3), integers);
    }

    @Test
    public void givenListOfPersonObject_whenSortedUsingCustomOrdering_shouldSortProperly() {
        //given
        List<Person> persons = Arrays.asList(new Person("Michael", 10), new Person("Alice", 3));
        Ordering<Person> orderingByAge = new Ordering<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return Ints.compare(p1.age, p2.age);
            }
        };

        //when
        persons.sort(orderingByAge);

        //then
        assertEquals(Arrays.asList(new Person("Alice", 3), new Person("Michael", 10)), persons);
    }

    @Test
    public void givenListOfPersonObject_whenSortedUsingChainedOrdering_shouldSortPropely() {
        //given
        List<Person> persons = Arrays.asList(new Person("Michael", 10), new Person("Alice", 3), new Person("Thomas", null));
        Ordering<Person> ordering = Ordering.natural().nullsFirst().onResultOf(new Function<Person, Comparable<Integer>>() {
            @Override
            public Comparable<Integer> apply(Person person) {
                return person.age;
            }
        });

        //when
        persons.sort(ordering);

        //then
        assertEquals(Arrays.asList(new Person("Thomas", null), new Person("Alice", 3), new Person("Michael", 10)), persons);
    }

    // dealing with null

    @Test
    public final void givenCollectionWithNulls_whenSortingWithNullsLast_thenNullsAreLast() {
        final List<Integer> toSort = Arrays.asList(3, 5, 4, null, 1, 2);
        Collections.sort(toSort, Ordering.natural().nullsLast());
        assertThat(toSort.get(toSort.size() - 1), nullValue());
    }

    @Test
    public final void givenCollectionWithNulls_whenSortingWithNullsFirst_thenNullsAreFirst() {
        final List<Integer> toSort = Arrays.asList(3, 5, 4, null, 1, 2);
        Collections.sort(toSort, Ordering.natural().nullsFirst());
        assertThat(toSort.get(0), nullValue());
    }

    @Test
    public final void whenCollectionIsSortedNullsLastReversed_thenNullAreFirst() {
        final List<Integer> toSort = Arrays.asList(3, 5, 4, null, 1, 2);
        Collections.sort(toSort, Ordering.natural().nullsLast().reverse());
        assertThat(toSort.get(0), nullValue());
    }

    // natural ordering

    @Test
    public final void whenSortingWithNaturalOrdering_thenCorectlySorted() {
        final List<Integer> toSort = Arrays.asList(3, 5, 4, 1, 2);
        Collections.sort(toSort, Ordering.natural());

        assertTrue(Ordering.natural().isOrdered(toSort));
    }

    // checking string ordering

    @Test
    public final void givenCollectionContainsDuplicates_whenCheckingStringOrdering_thenNo() {
        final List<Integer> toSort = Arrays.asList(3, 5, 4, 2, 1, 2);
        Collections.sort(toSort, Ordering.natural());

        assertFalse(Ordering.natural().isStrictlyOrdered(toSort));
    }

    // custom - by length

    @Test
    public final void givenCollectionIsSorted_whenUsingOrderingApiToCheckOrder_thenCheckCanBePerformed() {
        final List<String> toSort = Arrays.asList("zz", "aa", "b", "ccc");
        final Ordering<String> byLength = new OrderingByLenght();
        Collections.sort(toSort, byLength);

        final Ordering<String> expectedOrder = Ordering.explicit(Lists.newArrayList("b", "zz", "aa", "ccc"));
        assertTrue(expectedOrder.isOrdered(toSort));
    }

    @Test
    public final void whenSortingCollectionsOfStringsByLenght_thenCorrectlySorted() {
        final List<String> toSort = Arrays.asList("zz", "aa", "b", "ccc");
        final Ordering<String> byLength = new OrderingByLenght();

        Collections.sort(toSort, byLength);

        final Ordering<String> expectedOrder = Ordering.explicit(Lists.newArrayList("b", "zz", "aa", "ccc"));
        assertTrue(expectedOrder.isOrdered(toSort));
    }

    @Test
    public final void whenSortingCollectionsOfStringsByLenghtWithSecondaryNaturalOrdering_thenCorrectlySorted() {
        final List<String> toSort = Arrays.asList("zz", "aa", "b", "ccc");
        final Ordering<String> byLength = new OrderingByLenght();

        Collections.sort(toSort, byLength.compound(Ordering.natural()));

        final Ordering<String> expectedOrder = Ordering.explicit(Lists.newArrayList("b", "aa", "zz", "ccc"));
        assertTrue(expectedOrder.isOrdered(toSort));
    }

    @Test
    public final void whenSortingCollectionsWithComplexOrderingExample_thenCorrectlySorted() {
        final List<String> toSort = Arrays.asList("zz", "aa", null, "b", "ccc");

        Collections.sort(toSort, new OrderingByLenght().reverse().compound(Ordering.natural()).nullsLast());
        System.out.println(toSort);
    }

    // sorted copy

    @Test
    public final void givenUnorderdList_whenRetrievingSortedCopy_thenSorted() {
        final List<String> toSort = Arrays.asList("aa", "b", "ccc");
        final List<String> sortedCopy = new OrderingByLenght().sortedCopy(toSort);

        final Ordering<String> expectedOrder = Ordering.explicit(Lists.newArrayList("b", "aa", "ccc"));
        assertFalse(expectedOrder.isOrdered(toSort));
        assertTrue(expectedOrder.isOrdered(sortedCopy));
    }

    // to string

    @Test
    public final void givenUnorderdList_whenUsingToStringForSortingObjects_thenSortedWithToString() {
        final List<Integer> toSort = Arrays.asList(1, 2, 11);
        Collections.sort(toSort, Ordering.usingToString());

        final Ordering<Integer> expectedOrder = Ordering.explicit(Lists.newArrayList(1, 11, 2));
        assertTrue(expectedOrder.isOrdered(toSort));
    }

    // binary search

    @Test
    public final void whenPerformingBinarySearch_thenFound() {
        final List<Integer> toSort = Arrays.asList(1, 2, 11);
        //Collections.sort(toSort, Ordering.usingToString());
        //final int found = Ordering.usingToString().binarySearch(toSort, 2);
        final int found = Collections.binarySearch(toSort, 2, Ordering.usingToString());

        System.out.println(found);
    }

    // min/max without actually sorting

    @Test
    public final void whenFindingTheMinimalElementWithoutSorting_thenFound() {
        final List<Integer> toSort = Arrays.asList(2, 1, 11, 100, 8, 14);
        final int found = Ordering.natural().min(toSort);
        assertThat(found, equalTo(1));
    }

    @Test
    public final void whenFindingTheFirstFewElements_thenCorrect() {
        final List<Integer> toSort = Arrays.asList(2, 1, 11, 100, 8, 14);
        final List<Integer> leastOf = Ordering.natural().leastOf(toSort, 3);
        final List<Integer> expected = Lists.newArrayList(1, 2, 8);
        assertThat(expected, equalTo(leastOf));
    }

    // order the results of a Function

    @Test
    public final void givenListOfNumbers_whenRunningAToStringFunctionThenSorting_thenCorrect() {
        final List<Integer> toSort = Arrays.asList(2, 1, 11, 100, 8, 14);
        final Ordering<Object> ordering = Ordering.natural().onResultOf(Functions.toStringFunction());
        final List<Integer> sortedCopy = ordering.sortedCopy(toSort);

        final List<Integer> expected = Lists.newArrayList(1, 100, 11, 14, 2, 8);
        assertThat(expected, equalTo(sortedCopy));
    }

    private final class OrderingByLenght extends Ordering<String> {
        @Override
        public final int compare(final String s1, final String s2) {
            return Ints.compare(s1.length(), s2.length());
        }
    }
    
    class Person {
        private final String name;
        private final Integer age;

        private Person(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Person person = (Person) o;

            if (name != null ? !name.equals(person.name) : person.name != null) return false;
            return age != null ? age.equals(person.age) : person.age == null;

        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + (age != null ? age.hashCode() : 0);
            return result;
        }
    }
}
