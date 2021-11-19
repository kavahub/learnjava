package io.github.kavahub.learnjava;

import java.net.URL;
import java.net.URLConnection;

import io.github.kavahub.learnjava.util.UrlConnection;

public class ProxyWithSystemPropertyExample {
    public static final String RESOURCE_URL = "http://cn.bing.com";

    public static void main(String[] args) throws Exception {

        System.setProperty("https.proxyHost", "127.0.0.1");
        System.setProperty("https.proxyPort", "3128");

        URL url = new URL(RESOURCE_URL);
        URLConnection con = url.openConnection();
        System.out.println(UrlConnection.contentAsString(con));

        // 清除变量
        System.clearProperty("https.proxyHost");
        // proxy will no longer be used for http connections
    }  
}
