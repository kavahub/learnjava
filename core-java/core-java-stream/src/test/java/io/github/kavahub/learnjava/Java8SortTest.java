package io.github.kavahub.learnjava;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;

import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 集合排序
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class Java8SortTest {
    @Test
    public final void givenPreLambda_whenSortingEntitiesByName_thenCorrectlySorted() {
        final List<Human> humans = Lists.newArrayList(new Human("Sarah", 10), new Human("Jack", 12));

        Collections.sort(humans, new Comparator<Human>() {
            @Override
            public final int compare(final Human h1, final Human h2) {
                return h1.getName().compareTo(h2.getName());
            }
        });

        assertThat(humans.get(0), equalTo(new Human("Jack", 12)));
    }

    @Test
    public final void whenSortingEntitiesByName_thenCorrectlySorted() {
        final List<Human> humans = Lists.newArrayList(new Human("Sarah", 10), new Human("Jack", 12));

        humans.sort((final Human h1, final Human h2) -> h1.getName().compareTo(h2.getName()));

        assertThat(humans.get(0), equalTo(new Human("Jack", 12)));
    }

    @Test
    public final void givenLambdaShortForm_whenSortingEntitiesByName_thenCorrectlySorted() {
        final List<Human> humans = Lists.newArrayList(new Human("Sarah", 10), new Human("Jack", 12));

        humans.sort((h1, h2) -> h1.getName().compareTo(h2.getName()));

        assertThat(humans.get(0), equalTo(new Human("Jack", 12)));
    }

    @Test
    public final void whenSortingEntitiesByNameThenAge_thenCorrectlySorted() {
        final List<Human> humans = Lists.newArrayList(new Human("Sarah", 12), new Human("Sarah", 10),
                new Human("Zack", 12));
        humans.sort((lhs, rhs) -> {
            if (lhs.getName().equals(rhs.getName())) {
                return Integer.compare(lhs.getAge(), rhs.getAge());
            } else {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        assertThat(humans.get(0), equalTo(new Human("Sarah", 10)));
    }

    @Test
    public final void givenCompositionVerbose_whenSortingEntitiesByNameThenAge_thenCorrectlySorted() {
        final List<Human> humans = Lists.newArrayList(new Human("Sarah", 12), new Human("Sarah", 10),
                new Human("Zack", 12));
        final Comparator<Human> byName = (h1, h2) -> h1.getName().compareTo(h2.getName());
        final Comparator<Human> byAge = (h1, h2) -> Ints.compare(h1.getAge(), h2.getAge());

        humans.sort(byName.thenComparing(byAge));
        assertThat(humans.get(0), equalTo(new Human("Sarah", 10)));
    }

    @Test
    public final void givenComposition_whenSortingEntitiesByNameThenAge_thenCorrectlySorted() {
        final List<Human> humans = Lists.newArrayList(new Human("Sarah", 12), new Human("Sarah", 10),
                new Human("Zack", 12));

        humans.sort(Comparator.comparing(Human::getName).thenComparing(Human::getAge));
        assertThat(humans.get(0), equalTo(new Human("Sarah", 10)));
    }

    @Test
    public final void whenSortingEntitiesByAge_thenCorrectlySorted() {
        final List<Human> humans = Lists.newArrayList(new Human("Sarah", 10), new Human("Jack", 12));

        humans.sort((h1, h2) -> Ints.compare(h1.getAge(), h2.getAge()));
        assertThat(humans.get(0), equalTo(new Human("Sarah", 10)));
    }

    @Test
    public final void whenSortingEntitiesByNameReversed_thenCorrectlySorted() {
        final List<Human> humans = Lists.newArrayList(new Human("Sarah", 10), new Human("Jack", 12));
        final Comparator<Human> comparator = (h1, h2) -> h1.getName().compareTo(h2.getName());

        humans.sort(comparator.reversed());
        assertThat(humans.get(0), equalTo(new Human("Sarah", 10)));
    }

    @Test
    public final void givenMethodDefinition_whenSortingEntitiesByNameThenAge_thenCorrectlySorted() {
        final List<Human> humans = Lists.newArrayList(new Human("Sarah", 10), new Human("Jack", 12));

        humans.sort(Human::compareByNameThenAge);
        assertThat(humans.get(0), equalTo(new Human("Jack", 12)));
    }

    @Test
    public final void givenInstanceMethod_whenSortingEntitiesByName_thenCorrectlySorted() {
        final List<Human> humans = Lists.newArrayList(new Human("Sarah", 10), new Human("Jack", 12));

        humans.sort(Comparator.comparing(Human::getName));
        assertThat(humans.get(0), equalTo(new Human("Jack", 12)));
    }

    @Test
    public final void givenStreamNaturalOrdering_whenSortingEntitiesByName_thenCorrectlySorted() {
        final List<String> letters = Lists.newArrayList("B", "A", "C");

        final List<String> sortedLetters = letters.stream().sorted().collect(Collectors.toList());
        assertThat(sortedLetters.get(0), equalTo("A"));
    }

    @Test
    public final void givenStreamCustomOrdering_whenSortingEntitiesByName_thenCorrectlySorted() {
        final List<Human> humans = Lists.newArrayList(new Human("Sarah", 10), new Human("Jack", 12));
        final Comparator<Human> nameComparator = (h1, h2) -> h1.getName().compareTo(h2.getName());

        final List<Human> sortedHumans = humans.stream().sorted(nameComparator).collect(Collectors.toList());
        assertThat(sortedHumans.get(0), equalTo(new Human("Jack", 12)));
    }

    @Test
    public final void givenStreamComparatorOrdering_whenSortingEntitiesByName_thenCorrectlySorted() {
        final List<Human> humans = Lists.newArrayList(new Human("Sarah", 10), new Human("Jack", 12));

        final List<Human> sortedHumans = humans.stream().sorted(Comparator.comparing(Human::getName))
                .collect(Collectors.toList());
        assertThat(sortedHumans.get(0), equalTo(new Human("Jack", 12)));
    }

    @Test
    public final void givenStreamNaturalOrdering_whenSortingEntitiesByNameReversed_thenCorrectlySorted() {
        final List<String> letters = Lists.newArrayList("B", "A", "C");

        final List<String> reverseSortedLetters = letters.stream().sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        assertThat(reverseSortedLetters.get(0), equalTo("C"));
    }

    @Test
    public final void givenStreamCustomOrdering_whenSortingEntitiesByNameReversed_thenCorrectlySorted() {
        final List<Human> humans = Lists.newArrayList(new Human("Sarah", 10), new Human("Jack", 12));
        final Comparator<Human> reverseNameComparator = (h1, h2) -> h2.getName().compareTo(h1.getName());

        final List<Human> reverseSortedHumans = humans.stream().sorted(reverseNameComparator)
                .collect(Collectors.toList());
        assertThat(reverseSortedHumans.get(0), equalTo(new Human("Sarah", 10)));
    }

    @Test
    public final void givenStreamComparatorOrdering_whenSortingEntitiesByNameReversed_thenCorrectlySorted() {
        final List<Human> humans = Lists.newArrayList(new Human("Sarah", 10), new Human("Jack", 12));

        final List<Human> reverseSortedHumans = humans.stream()
                .sorted(Comparator.comparing(Human::getName, Comparator.reverseOrder())).collect(Collectors.toList());
        assertThat(reverseSortedHumans.get(0), equalTo(new Human("Sarah", 10)));
    }

    @Test
    public final void givenANullElement_whenSortingEntitiesByName_thenThrowsNPE() {
        final List<Human> humans = Lists.newArrayList(null, new Human("Jack", 12));

        assertThrows(NullPointerException.class, () -> humans.sort((h1, h2) -> h1.getName().compareTo(h2.getName())));
    }

    @Test
    public final void givenANullElement_whenSortingEntitiesByNameManually_thenMovesTheNullToLast() {
        final List<Human> humans = Lists.newArrayList(null, new Human("Jack", 12), null);

        // null last
        humans.sort((h1, h2) -> {
            if (h1 == null)
                return h2 == null ? 0 : 1;
            else if (h2 == null)
                return -1;

            return h1.getName().compareTo(h2.getName());
        });

        assertNotNull(humans.get(0));
        assertNull(humans.get(1));
        assertNull(humans.get(2));
    }

    @Test
    public final void givenANullElement_whenSortingEntitiesByName_thenMovesTheNullToLast() {
        final List<Human> humans = Lists.newArrayList(null, new Human("Jack", 12), null);

        humans.sort(Comparator.nullsLast(Comparator.comparing(Human::getName)));

        assertNotNull(humans.get(0));
        assertNull(humans.get(1));
        assertNull(humans.get(2));
    }

    @Test
    public final void givenANullElement_whenSortingEntitiesByName_thenMovesTheNullToStart() {
        final List<Human> humans = Lists.newArrayList(null, new Human("Jack", 12), null);

        humans.sort(Comparator.nullsFirst(Comparator.comparing(Human::getName)));

        assertNull(humans.get(0));
        assertNull(humans.get(1));
        assertNotNull(humans.get(2));
    }

    @Getter
    @Setter
    public static class Human {
        private String name;
        private int age;
    
        public Human() {
            super();
        }
    
        public Human(final String name, final int age) {
            super();
    
            this.name = name;
            this.age = age;
        }
    
        // API
        
        // compare
    
        public static int compareByNameThenAge(final Human lhs, final Human rhs) {
            if (lhs.name.equals(rhs.name)) {
                return Integer.compare(lhs.age, rhs.age);
            } else {
                return lhs.name.compareTo(rhs.name);
            }
        }
    
        //
    
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + age;
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            return result;
        }
    
        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Human other = (Human) obj;
            if (age != other.age) {
                return false;
            }
            if (name == null) {
                if (other.name != null) {
                    return false;
                }
            } else if (!name.equals(other.name)) {
                return false;
            }
            return true;
        }
    
        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            builder.append("Human [name=").append(name).append(", age=").append(age).append("]");
            return builder.toString();
        }
    }
    
}
