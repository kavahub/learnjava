package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

/**
 * 正则表达式 {@link Pattern} 复用
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class PatternReuseTest {
    @Test
    public void givenPreCompiledPattern_whenCallAsMatchPredicate_thenReturnMatchPredicateToMatchesPattern() {
        List<String> namesToValidate = Arrays.asList("Fabio Silva", "Fabio Luis Silva");
        Pattern firstLastNamePreCompiledPattern = Pattern.compile("[a-zA-Z]{3,} [a-zA-Z]{3,}");

        // 转换成断言接口， 了解下与 asPredicate 方法的区别
        Predicate<String> patternAsMatchPredicate = firstLastNamePreCompiledPattern.asMatchPredicate();
        List<String> validatedNames = namesToValidate.stream()
                .filter(patternAsMatchPredicate)
                .collect(Collectors.toList());

        assertTrue(validatedNames.contains("Fabio Silva"));
        assertFalse(validatedNames.contains("Fabio Luis Silva"));
    }   
}
