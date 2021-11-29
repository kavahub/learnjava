package io.github.kavahub.learnjava;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
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
public class ProxyWithWebExample {
    private static final String URL_STRING = "http://cn.bing.com";

    public static void main(String... args) throws IOException {

        URL weburl = new URL(URL_STRING);
        Proxy webProxy 
          = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 3128));
        HttpURLConnection webProxyConnection 
            = (HttpURLConnection) weburl.openConnection(webProxy);
        System.out.println(UrlConnection.contentAsString(webProxyConnection));
    }   
}
