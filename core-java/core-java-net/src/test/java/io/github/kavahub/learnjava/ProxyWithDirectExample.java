package io.github.kavahub.learnjava;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

import io.github.kavahub.learnjava.util.UrlConnection;

/**
 * 
 * Http 代理
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ProxyWithDirectExample {
    private static final String URL_STRING = "http://cn.bing.com";

    public static void main(String... args) throws IOException {

        URL weburl = new URL(URL_STRING);
        HttpURLConnection directConnection 
          = (HttpURLConnection) weburl.openConnection(Proxy.NO_PROXY);
        System.out.println(UrlConnection.contentAsString(directConnection));
    }    
}
