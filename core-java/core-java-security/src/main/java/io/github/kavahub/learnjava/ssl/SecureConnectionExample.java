package io.github.kavahub.learnjava.ssl;

import java.io.InputStream;
import java.io.OutputStream;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * ssl 客户端
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
public class SecureConnectionExample {
    public String host = "127.0.0.1";
    public Integer port = 8443;

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("args is not provided, use the default");
        }

        SecureConnectionExample client = new SecureConnectionExample();
        client.setHost(args);
        client.setPort(args);
        client.connectToServer();
    }
    
    public void connectToServer() {
        try {
            log.debug("Begin to connect {} {} ...", host, port);

            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket(host, port);
            InputStream in = sslsocket.getInputStream();
            OutputStream out = sslsocket.getOutputStream();
            
            out.write(1);
            
            while (in.available() > 0) {
                System.out.print(in.read());
            }
            
            log.debug("Secured connection performed successfully");
            
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void setHost(String[] args) {
        if (args.length == 2) {
            host = args[0];
        }
    }
    
    public void setPort(String[] args) {
        if (args.length == 2) {
            port = Integer.parseInt(args[1]);
        }
    }    
}
