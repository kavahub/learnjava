package io.github.kavahub.learnjava.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServerExample {
    private ServerSocket serverSocket;
    private Socket connectedSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while(true) {
            connectedSocket = serverSocket.accept();
            connectedSocket.setSoTimeout(40000);

            InetSocketAddress socketAddress = (InetSocketAddress) connectedSocket.getRemoteSocketAddress();
            String clientIpAddress = socketAddress.getAddress()
                .getHostAddress();
            System.out.println("IP address of the connected client :: " + clientIpAddress);

            out = new PrintWriter(connectedSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(connectedSocket.getInputStream()));
            String msg = in.readLine();
            System.out.println("Message received from the client :: " + msg);
            out.println("Hello Client !!");
            
            if ("bye".equals(msg)) {
                break;
            }
        }
        closeIO();
        stopServer();
    }

    private void closeIO() throws IOException {
        in.close();
        out.close();
    }

    private void stopServer() throws IOException {
        connectedSocket.close();
        serverSocket.close();
    }

    public static void main(String[] args) throws IOException {
        SimpleServerExample server = new SimpleServerExample();
        server.startServer(5000);
    }    
}
