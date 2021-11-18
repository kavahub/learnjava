package io.github.kavahub.learnjava.util;

import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * 
 * <p>
 * 关于参数化测试：
 * 
 * <p>
 * 通常，会遇到这样的情况，同一个测试案例，改变的只是测试时候输入的参数不同。
 * 按照之前的做法，可能会是通过每个输入参数都写一个测试，或者将测试参数封装到集合中循环遍历执行测试。
 * 在新版的Junit5中，已经提供了一种更加优雅的方式来进行。该特性允许我们：该特性可以让我们运行单个测试多次， 且使得每次运行仅仅是参数不同而已。
 * 
 * <p>
 */
public class RemoveLeadingAndTrailingZeroesTest {
    public static Stream<Arguments> leadingZeroTestProvider() {
        return Stream.of(Arguments.of("", ""), Arguments.of("abc", "abc"), Arguments.of("123", "123"),
                Arguments.of("0abc", "abc"), Arguments.of("0123", "123"), Arguments.of("0000123", "123"),
                Arguments.of("1230", "1230"), Arguments.of("01230", "1230"), Arguments.of("01", "1"),
                Arguments.of("0001", "1"), Arguments.of("0", "0"), Arguments.of("00", "0"), Arguments.of("0000", "0"),
                Arguments.of("12034", "12034"), Arguments.of("1200034", "1200034"), Arguments.of("0012034", "12034"),
                Arguments.of("1203400", "1203400"));
    }

    public static Stream<Arguments> trailingZeroTestProvider() {
        return Stream.of(Arguments.of("", ""), Arguments.of("abc", "abc"), Arguments.of("123", "123"),
                Arguments.of("abc0", "abc"), Arguments.of("1230", "123"), Arguments.of("1230000", "123"),
                Arguments.of("0123", "0123"), Arguments.of("01230", "0123"), Arguments.of("10", "1"),
                Arguments.of("1000", "1"), Arguments.of("0", "0"), Arguments.of("00", "0"), Arguments.of("0000", "0"),
                Arguments.of("12034", "12034"), Arguments.of("1200034", "1200034"), Arguments.of("0012034", "0012034"),
                Arguments.of("1203400", "12034"));
    }

    @ParameterizedTest
    @MethodSource("leadingZeroTestProvider")
    public void givenTestStrings_whenRemoveLeadingZeroesWithStringBuilder_thenReturnWithoutLeadingZeroes(String input,
            String expected) {
        // given

        // when
        String result = RemoveLeadingAndTrailingZeroes.removeLeadingZeroesWithStringBuilder(input);

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("trailingZeroTestProvider")
    public void givenTestStrings_whenRemoveTrailingZeroesWithStringBuilder_thenReturnWithoutTrailingZeroes(String input,
            String expected) {
        // given

        // when
        String result = RemoveLeadingAndTrailingZeroes.removeTrailingZeroesWithStringBuilder(input);

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("leadingZeroTestProvider")
    public void givenTestStrings_whenRemoveLeadingZeroesWithSubstring_thenReturnWithoutLeadingZeroes(String input,
            String expected) {
        // given

        // when
        String result = RemoveLeadingAndTrailingZeroes.removeLeadingZeroesWithSubstring(input);

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("trailingZeroTestProvider")
    public void givenTestStrings_whenRemoveTrailingZeroesWithSubstring_thenReturnWithoutTrailingZeroes(String input,
            String expected) {
        // given

        // when
        String result = RemoveLeadingAndTrailingZeroes.removeTrailingZeroesWithSubstring(input);

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("leadingZeroTestProvider")
    public void givenTestStrings_whenRemoveLeadingZeroesWithApacheCommonsStripStart_thenReturnWithoutLeadingZeroes(
            String input, String expected) {
        // given

        // when
        String result = RemoveLeadingAndTrailingZeroes.removeLeadingZeroesWithApacheCommonsStripStart(input);

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("trailingZeroTestProvider")
    public void givenTestStrings_whenRemoveTrailingZeroesWithApacheCommonsStripEnd_thenReturnWithoutTrailingZeroes(
            String input, String expected) {
        // given

        // when
        String result = RemoveLeadingAndTrailingZeroes.removeTrailingZeroesWithApacheCommonsStripEnd(input);

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("leadingZeroTestProvider")
    public void givenTestStrings_whenRemoveLeadingZeroesWithGuavaTrimLeadingFrom_thenReturnWithoutLeadingZeroes(
            String input, String expected) {
        // given

        // when
        String result = RemoveLeadingAndTrailingZeroes.removeLeadingZeroesWithGuavaTrimLeadingFrom(input);

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("trailingZeroTestProvider")
    public void givenTestStrings_whenRemoveTrailingZeroesWithGuavaTrimTrailingFrom_thenReturnWithoutTrailingZeroes(
            String input, String expected) {
        // given

        // when
        String result = RemoveLeadingAndTrailingZeroes.removeTrailingZeroesWithGuavaTrimTrailingFrom(input);

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("leadingZeroTestProvider")
    public void givenTestStrings_whenRemoveLeadingZeroesWithRegex_thenReturnWithoutLeadingZeroes(String input,
            String expected) {
        // given

        // when
        String result = RemoveLeadingAndTrailingZeroes.removeLeadingZeroesWithRegex(input);

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("trailingZeroTestProvider")
    public void givenTestStrings_whenRemoveTrailingZeroesWithRegex_thenReturnWithoutTrailingZeroes(String input,
            String expected) {
        // given

        // when
        String result = RemoveLeadingAndTrailingZeroes.removeTrailingZeroesWithRegex(input);

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }

}
