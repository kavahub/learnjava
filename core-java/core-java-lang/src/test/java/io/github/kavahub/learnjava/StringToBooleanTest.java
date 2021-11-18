package io.github.kavahub.learnjava;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class StringToBooleanTest {
    @Test
    public void givenStringTrue_whenUsingParseBoolean_thenTrue() {
        assertThat(Boolean.parseBoolean("true")).isTrue();
    }

    @Test
    public void givenStringTrue_whenUsingValueOf_thenTrue() {
        assertThat(Boolean.valueOf("true")).isTrue();
    }

    @Test
    public void givenStringTrue_whenUsingGetBoolean_thenFalse() {
        assertThat(Boolean.getBoolean("true")).isFalse();
    }

    @Test
    public void givenSystemProperty_whenUsingGetBoolean_thenTrue() {
        System.setProperty("CODING_IS_FUN", "true");
        
        assertThat(Boolean.getBoolean("CODING_IS_FUN")).isTrue();
    }    
}