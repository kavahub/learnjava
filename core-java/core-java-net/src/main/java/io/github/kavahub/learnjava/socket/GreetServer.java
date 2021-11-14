package io.github.kavahub.learnjava.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GreetServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) {
        log.info("Greet Server is running at port {}", port);
        try {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                log.info("Greet Server receive message: {}", inputLine);

                if (".".equals(inputLine)) {
                    out.println("good bye");
                    break;
                }

                if ("hello server".equals(inputLine))
                    out.println("hello client");
                else
                    out.println("unrecognised greeting");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }finally {
            stop();
        }

        
    }

    public void stop() {
        try {
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        log.info("Greet Server is closed");
    }

    public static void main(String[] args) {
        GreetServer server = new GreetServer();
        server.start(6666);
    }    
}
