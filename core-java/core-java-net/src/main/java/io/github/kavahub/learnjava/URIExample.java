package io.github.kavahub.learnjava;

import java.io.BufferedReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class URIExample {
    String URISTRING = "https://wordpress.org:443/support/topic/page-jumps-within-wordpress/?replies=3#post-2278484";
    // parsed locator
    String URISCHEME = "https";
    String URISCHEMESPECIFIC;
    String URIHOST = "wordpress.org";
    String URIAUTHORITY = "wordpress.org:443";

    String URIPATH = "/support/topic/page-jumps-within-wordpress/";
    int URIPORT = 443;
    String URIQUERY = "replies=3";
    String URIFRAGMENT = "post-2278484";
    String URIUSERINFO;
    String URICOMPOUND = URISCHEME + "://" + URIHOST + ":" + URIPORT + URIPATH + "?" + URIQUERY + "#" + URIFRAGMENT;

    URI uri;
    URL url;
    BufferedReader in = null;
    String URIContent = "";

    private String getParsedPieces(URI uri) {
        log.info("*** List of parsed pieces ***");
        URISCHEME = uri.getScheme();
        log.info("URISCHEME: " + URISCHEME);
        URISCHEMESPECIFIC = uri.getSchemeSpecificPart();
        log.info("URISCHEMESPECIFIC: " + URISCHEMESPECIFIC);
        URIHOST = uri.getHost();
        URIAUTHORITY = uri.getAuthority();
        log.info("URIAUTHORITY: " + URIAUTHORITY);
        log.info("URIHOST: " + URIHOST);
        URIPATH = uri.getPath();
        log.info("URIPATH: " + URIPATH);
        URIPORT = uri.getPort();
        log.info("URIPORT: " + URIPORT);
        URIQUERY = uri.getQuery();
        log.info("URIQUERY: " + URIQUERY);
        URIFRAGMENT = uri.getFragment();
        log.info("URIFRAGMENT: " + URIFRAGMENT);

        try {
            url = uri.toURL();
        } catch (MalformedURLException e) {
            log.info("MalformedURLException thrown: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            log.info("IllegalArgumentException thrown: " + e.getMessage());
            e.printStackTrace();
        }
        return url.toString();
    }

    public String testURIAsNew(String URIString) {
        // creating URI object
        try {
            uri = new URI(URIString);
        } catch (URISyntaxException e) {
            log.info("URISyntaxException thrown: " + e.getMessage());
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
        return getParsedPieces(uri);
    }

    public String testURIAsCreate(String URIString) {
        // create方法是方便的，内部调用new创建对象
        // creating URI object
        uri = URI.create(URIString);
        return getParsedPieces(uri);
    }

    public static void main(String[] args) throws Exception {
        URIExample demo = new URIExample();
        String contentCreate = demo.testURIAsCreate(demo.URICOMPOUND);
        log.info(contentCreate);
        String contentNew = demo.testURIAsNew(demo.URICOMPOUND);
        log.info(contentNew);
    }    
}
