package io.github.kavahub.learnjava;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.URL;

import io.github.kavahub.learnjava.util.UrlConnection;

/**
 * 
 * Http 代理
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ProxyWithSocksExample {
    private static final String URL_STRING = "http://cn.bing.com";
    private static final String SOCKET_SERVER_HOST = "learnjava.net";
    private static final int SOCKET_SERVER_PORT = 1111;

    public static void main(String... args) throws IOException, InterruptedException {

        URL weburl = new URL(URL_STRING);
        Proxy socksProxy 
          = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 3128));
        HttpURLConnection socksConnection 
          = (HttpURLConnection) weburl.openConnection(socksProxy);
        System.out.println(UrlConnection.contentAsString(socksConnection));

        Socket proxySocket = new Socket(socksProxy);
        InetSocketAddress socketHost 
          = new InetSocketAddress(SOCKET_SERVER_HOST, SOCKET_SERVER_PORT);
        proxySocket.connect(socketHost);
        // do stuff with the socket

        proxySocket.close();
    }    
}
