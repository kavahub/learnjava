package io.github.kavahub.learnjava;

import java.net.URL;
import java.net.URLConnection;

import io.github.kavahub.learnjava.util.UrlConnection;

/**
 * 
 * Http 代理
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ProxyWithCommandLineExample {
    public static final String RESOURCE_URL = "http://cn.bing.com";

    public static void main(String[] args) throws Exception {

        URL url = new URL(RESOURCE_URL);
        URLConnection con = url.openConnection();
        System.out.println(UrlConnection.contentAsString(con));
    }   
}
