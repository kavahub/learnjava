package io.github.kavahub.learnjava.ssl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnableTLSv12Example {   
    public String url = "127.0.0.1";
    public Integer port = 8443;

    public EnableTLSv12Example() {
    }

    public static void main(String[] args) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        if (args.length != 2) {
            System.out.println("args is not provided, use the default");
        }
        EnableTLSv12Example client = new EnableTLSv12Example();

        client.setHost(args);
        client.setPort(args);
        client.enableTLSv12UsingHttpConnection();
        client.enableTLSv12UsingProtocol();
        client.enableTLSv12UsingSSLContext();
        client.enableTLSv12UsingSSLParameters();
    }

    private void setPort(String[] args) {
        if (args.length == 2) {
            url = args[0];
        }
    }

    private void setHost(String[] args) {
        if (args.length == 2) {
            String portNumber = args[1];
            port = Integer.parseInt(portNumber);
        }
    }

    private void handleCommunication(SSLSocket socket, String usedTLSProcess) throws IOException {
        log.debug("Enabled TLS v1.2 on " + usedTLSProcess);
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            out.println("GET / HTTP/1.0");
            out.println();
            out.flush();
            if (out.checkError()) {
                log.error("SSLSocketClient:  java.io.PrintWriter error");
                return;
            }

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                log.info(inputLine);
        }
    }

    public void enableTLSv12UsingSSLParameters() throws UnknownHostException, IOException {
        SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslSocket = (SSLSocket) socketFactory.createSocket(url.trim(), port);
        SSLParameters params = new SSLParameters();
        params.setProtocols(new String[] { "TLSv1.2" });
        sslSocket.setSSLParameters(params);
        sslSocket.startHandshake();
        handleCommunication(sslSocket, "SSLSocketFactory-SSLParameters");
    }

    public void enableTLSv12UsingProtocol() throws IOException {
        SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslSocket = (SSLSocket) socketFactory.createSocket(url, port);
        sslSocket.setEnabledProtocols(new String[] { "TLSv1.2" });
        sslSocket.startHandshake();
        handleCommunication(sslSocket, "SSLSocketFactory-EnabledProtocols");
    }

    public void enableTLSv12UsingHttpConnection() throws IOException, NoSuchAlgorithmException, KeyManagementException {
        URL urls = new URL("https://" + url + ":" + port);
        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(null, null, new SecureRandom());
        HttpsURLConnection connection = (HttpsURLConnection) urls.openConnection();
        connection.setSSLSocketFactory(sslContext.getSocketFactory());
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String input;
            while ((input = br.readLine()) != null) {
                log.info(input);
            }
        }
        log.debug("Created TLSv1.2 connection on HttpsURLConnection");
    }

    public void enableTLSv12UsingSSLContext()
            throws NoSuchAlgorithmException, KeyManagementException, UnknownHostException, IOException {
        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(null, null, new SecureRandom());
        SSLSocketFactory socketFactory = sslContext.getSocketFactory();
        SSLSocket socket = (SSLSocket) socketFactory.createSocket(url, port);
        handleCommunication(socket, "SSLContext");
    }
}
