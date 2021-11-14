package io.github.kavahub.learnjava.ssl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.net.SocketFactory;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleClientExample {
    public void connect(String host, int port) throws IOException {
        SocketFactory factory = SSLSocketFactory.getDefault();

        log.debug("connect to server {} {} ", host, port);
        try (Socket connection = factory.createSocket(host, port)) {
            //((SSLSocket) connection).setEnabledCipherSuites(new String[] { "TLS_DHE_DSS_WITH_AES_256_CBC_SHA256" });
            ((SSLSocket) connection).setEnabledProtocols(new String[] { "TLSv1.2" });
            SSLParameters sslParams = new SSLParameters();
            sslParams.setEndpointIdentificationAlgorithm("HTTPS");
            ((SSLSocket) connection).setSSLParameters(sslParams);
            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            log.debug(input.readLine());
        }
    }

    public static void main(String[] args) throws IOException {
        new SimpleClientExample().connect("localhost", 8443);
    }
}
