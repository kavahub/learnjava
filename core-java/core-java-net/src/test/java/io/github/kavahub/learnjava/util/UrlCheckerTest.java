package io.github.kavahub.learnjava.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import static io.github.kavahub.learnjava.util.UrlChecker.*;

/**
 * 
 * {@link UrlChecker} 应用示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class UrlCheckerTest {
    @Test
    public void givenValidUrl_WhenUsingHEAD_ThenReturn200() throws IOException {
        int responseCode = getResponseCodeForURLUsingHead("http://www.example.com");
        assertEquals(200, responseCode);
    }
    
    @Test
    public void givenInvalidIUrl_WhenUsingHEAD_ThenReturn404() throws IOException {
        int responseCode = getResponseCodeForURLUsingHead("http://www.example.com/unkownurl");
        assertEquals(404, responseCode);
    }
    
    @Test
    public void givenValidUrl_WhenUsingGET_ThenReturn200() throws IOException {
        int responseCode = getResponseCodeForURL("http://www.example.com");
        assertEquals(200, responseCode);
    }
    
    @Test
    public void givenInvalidIUrl_WhenUsingGET_ThenReturn404() throws IOException {
        int responseCode = getResponseCodeForURL("http://www.example.com/unkownurl");
        assertEquals(404, responseCode);
    }   
}
