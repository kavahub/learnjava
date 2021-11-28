package io.github.kavahub.learnjava;

import java.time.LocalDate;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.primitives.Ints;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import lombok.Getter;

import static org.assertj.core.api.Assertions.*;

/**
 * {@link Objects} 工具比较对象
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class CompareUseGuavaTest {
    @Nested
    class ObjectsEqualMethod {
        @Test
        void givenTwoStringsWithSameValues_whenObjectsEqualMethods_thenTrue() {
            String a = new String("Hello!");
            String b = new String("Hello!");

            assertThat(Objects.equal(a, b)).isTrue();
        }

        @Test
        void givenTwoStringsWithDifferentValues_whenObjectsEqualMethods_thenFalse() {
            String a = new String("Hello!");
            String b = new String("Hello World!");

            assertThat(Objects.equal(a, b)).isFalse();
        }
    }

    @Nested
    class ComparisonMethods {
        @Test
        void givenTwoIntsWithConsecutiveValues_whenIntsCompareMethods_thenNegative() {
            int first = 1;
            int second = 2;
            assertThat(Ints.compare(first, second)).isNegative();
        }

        @Test
        void givenTwoIntsWithSameValues_whenIntsCompareMethods_thenZero() {
            int first = 1;
            int second = 1;

            assertThat(Ints.compare(first, second)).isZero();
        }

        @Test
        void givenTwoIntsWithConsecutiveValues_whenIntsCompareMethodsReversed_thenNegative() {
            int first = 1;
            int second = 2;

            assertThat(Ints.compare(second, first)).isPositive();
        }
    }

    @Nested
    class ComparisonChainClass {
        @Test
        void givenTwoPersonWithEquals_whenComparisonChainByLastNameThenFirstName_thenSortedJoeFirstAndNatalieSecond() {
            PersonWithEquals natalie = new PersonWithEquals("Natalie", "Portman");
            PersonWithEquals joe = new PersonWithEquals("Joe", "Portman");

            int comparisonResult = ComparisonChain.start()
              .compare(natalie.getLastName(), joe.getLastName())
              .compare(natalie.getFirstName(), joe.getFirstName())
              .result();

            assertThat(comparisonResult).isPositive();
        }
    }    

    @Getter
    public static class PersonWithEquals {
        private String firstName;
        private String lastName;
        private LocalDate birthDate;
    
        public PersonWithEquals(String firstName, String lastName) {
            if (firstName == null || lastName == null) {
                throw new NullPointerException("Names can't be null");
            }
            this.firstName = firstName;
            this.lastName = lastName;
        }
    
        public PersonWithEquals(String firstName, String lastName, LocalDate birthDate) {
            this(firstName, lastName);
    
            this.birthDate = birthDate;
        }
    
        
    
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PersonWithEquals that = (PersonWithEquals) o;
            return firstName.equals(that.firstName) &&
              lastName.equals(that.lastName) &&
              java.util.Objects.equals(birthDate, that.birthDate);
        }
    
        @Override
        public int hashCode() {
            return java.util.Objects.hash(firstName, lastName);
        }
    }
}
