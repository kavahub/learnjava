package io.github.kavahub.learnjava.http;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class ParameterStringBuilderTest {
    @Test
    public void givenNormalChar_whenGetParamsString() throws UnsupportedEncodingException {
        Map<String, String> params = Map.of("parma", "abc");
        assertEquals("parma=abc", ParameterStringBuilder.getParamsString(params));
    }

    @Test
    public void givenSpecialChar_whenGetParamsString() throws UnsupportedEncodingException {
        Map<String, String> params = Map.of("parma", "~!@#$%^&*()");
        assertEquals("parma=%7E%21%40%23%24%25%5E%26*%28%29", ParameterStringBuilder.getParamsString(params));
    }

    @Test
    public void givenChinaChar_whenGetParamsString() throws UnsupportedEncodingException {
        Map<String, String> params = Map.of("parma", "中文");
        assertEquals("parma=%E4%B8%AD%E6%96%87", ParameterStringBuilder.getParamsString(params));
    }
}
