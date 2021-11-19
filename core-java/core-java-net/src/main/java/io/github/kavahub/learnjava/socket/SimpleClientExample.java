package io.github.kavahub.learnjava.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SimpleClientExample {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void connect(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void sendGreetings(String msg) throws IOException {
        out.println(msg);
        String reply = in.readLine();
        System.out.println("Reply received from the server :: " + reply);
    }

    public void disconnect() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public static void main(String[] args) throws IOException {
        SimpleClientExample client = new SimpleClientExample();
        client.connect("127.0.0.1", 5000); // IP address and port number of the server
        client.sendGreetings("Hello, Welcome!"); // greetings message
        client.disconnect();
    }    
}
