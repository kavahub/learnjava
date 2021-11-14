package io.github.kavahub.learnjava.proxy;

import java.net.URL;
import java.net.URLConnection;

public class CommandLineProxyExample {
    public static final String RESOURCE_URL = "http://cn.bing.com";

    public static void main(String[] args) throws Exception {

        URL url = new URL(RESOURCE_URL);
        URLConnection con = url.openConnection();
        System.out.println(UrlConnectionHelper.contentAsString(con));
    }   
}
