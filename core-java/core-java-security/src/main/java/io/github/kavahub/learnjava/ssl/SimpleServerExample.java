package io.github.kavahub.learnjava.ssl;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * ssl 服务端
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
public class SimpleServerExample {
    void startServer(int port) throws IOException {
        ServerSocketFactory factory = SSLServerSocketFactory.getDefault();

        try (ServerSocket listener = factory.createServerSocket(port)) {
            log.debug("Server start on port {}", port);
            ((SSLServerSocket) listener).setNeedClientAuth(true);               
            ((SSLServerSocket) listener).setEnabledCipherSuites(new String[] { "TLS_DHE_DSS_WITH_ARIA_128_GCM_SHA256" });
            ((SSLServerSocket) listener).setEnabledProtocols(new String[] { "TLSv1.2" });
            while (true) {
                try (Socket socket = listener.accept()) {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("Hello World!");
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new SimpleServerExample().startServer(8443);
    }
}
