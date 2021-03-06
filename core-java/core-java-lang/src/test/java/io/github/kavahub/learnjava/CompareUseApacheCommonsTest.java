package io.github.kavahub.learnjava;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import java.util.Objects;

/**
 * {@link ObjectUtils} 工具比较对象
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class CompareUseApacheCommonsTest {
    @Test
    void givenTwoStringsWithSameValues_whenApacheCommonsEqualityMethods_thenEqualsTrueNotEqualsFalse() {
        String a = new String("Hello!");
        String b = new String("Hello!");

        // assertThat(ObjectUtils.equals(a, b)).isTrue();
        assertThat(Objects.equals(a, b)).isTrue();
        assertThat(ObjectUtils.notEqual(a, b)).isFalse();
    }

    @Test
    void givenTwoStringsWithDifferentValues_whenApacheCommonsEqualityMethods_thenEqualsFalseNotEqualsTrue() {
        String a = new String("Hello!");
        String b = new String("Hello World!");

        // assertThat(ObjectUtils.equals(a, b)).isFalse();
        assertThat(Objects.equals(a, b)).isFalse();
        assertThat(ObjectUtils.notEqual(a, b)).isTrue();
    }

    @Test
    void givenTwoStringsWithConsecutiveValues_whenApacheCommonsCompare_thenNegative() {
        String first = new String("Hello!");
        String second = new String("How are you?");

        assertThat(ObjectUtils.compare(first, second)).isNegative();
    }

    @Test
    void givenTwoStringsWithSameValues_whenApacheCommonsEqualityMethods_thenEqualsFalseNotEqualsTrue() {
        String first = new String("Hello!");
        String second = new String("Hello!");

        assertThat(ObjectUtils.compare(first, second)).isZero();
    }

    @Test
    void givenTwoStringsWithConsecutiveValues_whenApacheCommonsCompareReversed_thenPositive() {
        String first = new String("Hello!");
        String second = new String("How are you?");

        assertThat(ObjectUtils.compare(second, first)).isPositive();
    }

    @Test
    void givenTwoStringsOneNull_whenApacheCommonsCompare_thenPositive() {
        String first = new String("Hello!");
        String second = null;

        assertThat(ObjectUtils.compare(first, second, false)).isPositive();
    }   
}
