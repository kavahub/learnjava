package io.github.kavahub.learnjava.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EchoMultiClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            log.error(e.getMessage());
        }

    }

    public String sendMessage(String msg) {
        try {
            out.println(msg);
            return in.readLine();
        } catch (Exception e) {
            return null;
        }
    }

    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        log.info("Greet Client is closed");
    }   

    public static void main(String[] args) {
        // 测试服务器端线程
        // 1. 每个客户端连接，服务器端都会生成一个线程，线程处理各自的消息
        // 2. 客户端连接发送消息完成后，必须发送连接结束消息，否则服务器端异常（connect reset）
        // 3. 测试说明服务器使用BIO实现
        EchoMultiClient client1 = new EchoMultiClient();
        client1.startConnection("127.0.0.1", 5555);
        EchoMultiClient client2 = new EchoMultiClient();
        client2.startConnection("127.0.0.1", 5555);
        EchoMultiClient client3 = new EchoMultiClient();
        client3.startConnection("127.0.0.1", 5555);

        SendMessage sm1 = new SendMessage(client1, "client1");
        SendMessage sm2 = new SendMessage(client2, "client2");
        SendMessage sm3 = new SendMessage(client3, "client3");

        new Thread(sm1).start();
        new Thread(sm2).start();
        new Thread(sm3).start();
    }

    public static class SendMessage implements Runnable {
        private final EchoMultiClient client;
        private final String message;

        public SendMessage(EchoMultiClient client, String message) {
            this.client = client;
            this.message = message;
        }


        @Override
        public void run() {
            for(int i = 0; i <= 10; i++) {
                client.sendMessage(message + " - " + i);
            }
            client.sendMessage(".");
        }

    }
}
