package io.github.kavahub.learnjava;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EncoderDecoderTest {
    private static final String testUrl = "http://www.baeldung.com?key1=value+1&key2=value%40%21%242&key3=value%253";
    private static final String testUrlWithPath = "http://www.baeldung.com/path+1?key1=value+1&key2=value%40%21%242&key3=value%253";

    private String encodeValue(String value) {
        String encoded = null;
        try {
            encoded = URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            log.error("Error encoding parameter {}", e.getMessage(), e);
        }
        return encoded;
    }

    private String decodeValue(String value) {
        String decoded = null;
        try {
            decoded = URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            log.error("Error encoding parameter {}", e.getMessage(), e);
        }
        return decoded;
    }

    @Test
    public void givenURL_whenAnalyze_thenCorrect() throws Exception {
        URL url = new URL(testUrl);

        assertThat(url.getProtocol(), is("http"));
        assertThat(url.getHost(), is("www.baeldung.com"));
        assertThat(url.getQuery(), is("key1=value+1&key2=value%40%21%242&key3=value%253"));
    }

    @Test
    public void givenRequestParam_whenUTF8Scheme_thenEncode() throws Exception {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("key1", "value 1");
        requestParams.put("key2", "value@!$2");
        requestParams.put("key3", "value%3");

        String encodedURL = requestParams.keySet().stream().map(key -> key + "=" + encodeValue(requestParams.get(key)))
                .collect(Collectors.joining("&", "http://www.baeldung.com?", ""));

        assertThat(testUrl, is(encodedURL));
    }

    @Test
    public void givenRequestParam_whenUTF8Scheme_thenDecodeRequestParams() throws Exception {
        URL url = new URL(testUrl);

        String query = url.getQuery();

        String decodedQuery = Arrays.stream(query.split("&"))
                .map(param -> param.split("=")[0] + "=" + decodeValue(param.split("=")[1]))
                .collect(Collectors.joining("&"));

        assertEquals("http://www.baeldung.com?key1=value 1&key2=value@!$2&key3=value%3",
                url.getProtocol() + "://" + url.getHost() + "?" + decodedQuery);
    }

    @Test
    public void givenPathSegment_whenUsingSpring_thenEncodeDecode() throws UnsupportedEncodingException {
        String pathSegment = "/Path 1/Path+2";
        String encodedPathSegment = encodePath(pathSegment);
        String decodedPathSegment = UriUtils.decode(encodedPathSegment, "UTF-8");
        assertEquals("/Path%201/Path+2", encodedPathSegment);
        assertEquals("/Path 1/Path+2", decodedPathSegment);
    }

    private String encodePath(String path) {
        return UriUtils.encodePath(path, "UTF-8");
    }

    @Test
    public void givenPathAndRequestParam_whenUTF8Scheme_thenEncode() throws Exception {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("key1", "value 1");
        requestParams.put("key2", "value@!$2");
        requestParams.put("key3", "value%3");

        String path = "path+1";

        String encodedURL = requestParams.keySet().stream().map(key -> key + "=" + encodeValue(requestParams.get(key)))
                .collect(Collectors.joining("&", "http://www.baeldung.com/" + encodePath(path) + "?", ""));

        assertThat(testUrlWithPath, is(encodedURL));
    }
}
