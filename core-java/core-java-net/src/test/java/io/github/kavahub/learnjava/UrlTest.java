package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link URL} 应用示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class UrlTest {
    @Test
    public void givenUrl_whenCanIdentifyProtocol_thenCorrect() throws MalformedURLException {
        final URL url = new URL("http://learnjava.net");
        assertEquals("http", url.getProtocol());
    }

    @Test
    public void givenUrl_whenCanGetHost_thenCorrect() throws MalformedURLException {
        final URL url = new URL("http://learnjava.net");
        assertEquals("learnjava.net", url.getHost());
    }

    @Test
    public void givenUrl_whenCanGetFileName_thenCorrect2() throws MalformedURLException {
        final URL url = new URL("http://learnjava.net/articles?topic=java&version=8");
        assertEquals("/articles?topic=java&version=8", url.getFile());
    }

    @Test
    public void givenUrl_whenCanGetFileName_thenCorrect1() throws MalformedURLException {
        final URL url = new URL("http://learnjava.net/guidelines.txt");
        assertEquals("/guidelines.txt", url.getFile());
    }

    @Test
    public void givenUrl_whenCanGetPathParams_thenCorrect() throws MalformedURLException {
        final URL url = new URL("http://learnjava.net/articles?topic=java&version=8");
        assertEquals("/articles", url.getPath());
    }

    @Test
    public void givenUrl_whenCanGetQueryParams_thenCorrect() throws MalformedURLException {
        final URL url = new URL("http://learnjava.net/articles?topic=java");
        assertEquals("topic=java", url.getQuery());
    }

    @Test
    public void givenUrl_whenGetsDefaultPort_thenCorrect() throws MalformedURLException {
        final URL url = new URL("http://learnjava.net");
        assertEquals(-1, url.getPort());
        assertEquals(80, url.getDefaultPort());
    }

    @Test
    public void givenUrl_whenGetsPort_thenCorrect() throws MalformedURLException {
        final URL url = new URL("http://learnjava.net:8090");
        assertEquals(8090, url.getPort());
        assertEquals(80, url.getDefaultPort());
    }

    @Test
    public void givenBaseUrl_whenCreatesRelativeUrl_thenCorrect() throws MalformedURLException {
        final URL baseUrl = new URL("http://learnjava.net");
        final URL relativeUrl = new URL(baseUrl, "a-guide-to-java-sockets");
        assertEquals("http://learnjava.net/a-guide-to-java-sockets", relativeUrl.toString());
    }

    @Test
    public void givenAbsoluteUrl_whenIgnoresBaseUrl_thenCorrect() throws MalformedURLException {
        final URL baseUrl = new URL("http://learnjava.net");
        final URL relativeUrl = new URL(baseUrl, "http://learnjava.net/a-guide-to-java-sockets");
        assertEquals("http://learnjava.net/a-guide-to-java-sockets", relativeUrl.toString());
    }

    @Test
    public void givenUrlComponents_whenConstructsCompleteUrl_thenCorrect() throws MalformedURLException {
        final String protocol = "http";
        final String host = "learnjava.net";
        final String file = "/guidelines.txt";
        final URL url = new URL(protocol, host, file);
        assertEquals("http://learnjava.net/guidelines.txt", url.toString());
    }

    @Test
    public void givenUrlComponents_whenConstructsCompleteUrl_thenCorrect2() throws MalformedURLException {
        final String protocol = "http";
        final String host = "learnjava.net";
        final String file = "/articles?topic=java&version=8";
        final URL url = new URL(protocol, host, file);
        assertEquals("http://learnjava.net/articles?topic=java&version=8", url.toString());
    }

    @Test
    public void givenUrlComponentsWithPort_whenConstructsCompleteUrl_thenCorrect() throws MalformedURLException {
        final String protocol = "http";
        final String host = "learnjava.net";
        final int port = 9000;
        final String file = "/guidelines.txt";
        final URL url = new URL(protocol, host, port, file);
        assertEquals("http://learnjava.net:9000/guidelines.txt", url.toString());
    }   
}
