package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Splitter;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringToTests {   
    private final String countries = "Russia,Germany,England,France,Italy";
    private final List<String> expectedCountriesList = Arrays.asList("Russia", "Germany", "England", "France", "Italy");

    @Test
    public void toCharStream() {
        List<String> strings = "Tests".codePoints()
          .mapToObj(c -> String.valueOf((char) c))
          .collect(Collectors.toList());

        assertEquals(strings.size(), 5);
    }

    @Test
    public void toListByJava() {
        List<String> convertedCountriesList = Arrays.asList(countries.split(",", -1));

        assertEquals(expectedCountriesList, convertedCountriesList);
    }

    @Test
    public void toListByApache() {
        List<String> convertedCountriesList = Arrays.asList(StringUtils.splitPreserveAllTokens(countries, ","));

        assertEquals(expectedCountriesList, convertedCountriesList);
    }

    @Test
    public void toListByGuava() {
        List<String> convertedCountriesList = Splitter.on(",")
            .trimResults()
            .splitToList(countries);

        assertEquals(expectedCountriesList, convertedCountriesList);
    }

    @Test
    public void toInt() {
        String givenString = "42";

        int result = Integer.parseInt(givenString);

        assertThat(result).isEqualTo(42);
    }

    @Test
    public void toInt2() {
        String givenString = "101010";

        int result = Integer.parseInt(givenString, 2);

        assertThat(result).isEqualTo(42);
    }


    @Test
    public void toEnum() {
        String pizzaEnumValue = "READY";
        PizzaStatusEnum pizzaStatusEnum = PizzaStatusEnum.valueOf(pizzaEnumValue);
        assertTrue(pizzaStatusEnum == PizzaStatusEnum.READY);
    }
    
    @Test
    public void toEnumm_thenThrowsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> PizzaStatusEnum.valueOf("rEAdY"));
    }

    @Test
    public void toDouble() {
        assertEquals(1.23, Double.parseDouble("1.23"), 0.000001);
    }
}
