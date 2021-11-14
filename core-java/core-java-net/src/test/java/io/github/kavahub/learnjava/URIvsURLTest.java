package io.github.kavahub.learnjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.jupiter.api.Test;

/**
 * URI，统一资源标志符(Uniform Resource Identifier， URI)
 * URL是URI的一个子集。它是Uniform Resource Locator的缩写，译为“统一资源定位符”。
 */
public class URIvsURLTest {
    @Test
    public void whenCreatingURIs_thenSameInfo() throws URISyntaxException {
        URI firstURI = new URI("somescheme://theuser:thepassword@someauthority:80/some/path?thequery#somefragment");
        URI secondURI = new URI("somescheme", "theuser:thepassword", "someuthority", 80, "/some/path", "thequery", "somefragment");

        assertEquals(firstURI.getScheme(), secondURI.getScheme());
        assertEquals(firstURI.getPath(), secondURI.getPath());
    }

    @Test
    public void whenCreatingURLs_thenSameInfo() throws MalformedURLException {
        URL firstURL = new URL("http://theuser:thepassword@somehost:80/path/to/file?thequery#somefragment");
        URL secondURL = new URL("http", "somehost", 80, "/path/to/file");

        assertEquals(firstURL.getHost(), secondURL.getHost());
        assertEquals(firstURL.getPath(), secondURL.getPath());
    }

    @Test
    public void whenCreatingURI_thenCorrect() {
        URI uri = URI.create("urn:isbn:1234567890");

        assertNotNull(uri);
    }

    @Test
    public void whenCreatingURLs_thenException() throws MalformedURLException {
        assertThrows(MalformedURLException.class, () -> new URL("otherprotocol://somehost/path/to/file"));
    }

    @Test
    public void givenObjects_whenConverting_thenCorrect() throws MalformedURLException, URISyntaxException {
        String aURIString = "http://somehost:80/path?thequery";
        URI uri = new URI(aURIString);
        URL url = new URL(aURIString);

        URL toURL = uri.toURL();
        URI toURI = url.toURI();

        assertNotNull(url);
        assertNotNull(uri);
        assertEquals(toURL.toString(), toURI.toString());
    }

    @Test
    public void givenURI_whenConvertingToURL_thenException() throws MalformedURLException, URISyntaxException {
        URI uri = new URI("somescheme://someauthority/path?thequery");

        assertThrows(MalformedURLException.class, () -> uri.toURL());
    }
}
