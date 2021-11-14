package io.github.kavahub.learnjava.proxy;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.URL;

public class SocksProxyExample {
    private static final String URL_STRING = "http://cn.bing.com";
    private static final String SOCKET_SERVER_HOST = "learnjava.net";
    private static final int SOCKET_SERVER_PORT = 1111;

    public static void main(String... args) throws IOException, InterruptedException {

        URL weburl = new URL(URL_STRING);
        Proxy socksProxy 
          = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 3128));
        HttpURLConnection socksConnection 
          = (HttpURLConnection) weburl.openConnection(socksProxy);
        System.out.println(UrlConnectionHelper.contentAsString(socksConnection));

        Socket proxySocket = new Socket(socksProxy);
        InetSocketAddress socketHost 
          = new InetSocketAddress(SOCKET_SERVER_HOST, SOCKET_SERVER_PORT);
        proxySocket.connect(socketHost);
        // do stuff with the socket

        proxySocket.close();
    }    
}
