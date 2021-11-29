package io.github.kavahub.learnjava.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 服务器端，使用线程处理消息
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
public class EchoMultiServer {
    private static int index = 1;
    private ServerSocket serverSocket;

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            log.info("Echo Multi Server is running at port {}", port);
            
            while(true) {
                // accept方法阻塞进程，所以实例还未创建，很有意思的懒创建
                new EchoClientHandler(serverSocket.accept()).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stop();
        }

    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        log.info("Echo Multi Server is closed");
    }

    private static class EchoClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public EchoClientHandler(Socket socket) {
            this.clientSocket = socket;
            setName("Echo Client Handler - " + index++);
        }

        public void run() {
            
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (".".equals(inputLine)) {
                        out.println("bye");
                        break;
                    }
                    out.println(inputLine);

                    log.info("Received message[{}]: {}", Thread.currentThread().getName(), inputLine);
                    // 假设处理需要时间
                    TimeUnit.MILLISECONDS.sleep(200);
                }
            } catch (IOException | InterruptedException e) {
                log.error(e.getMessage());
            } finally {
                close();
            }
        }

        public void close() {
            try {
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        EchoMultiServer server = new EchoMultiServer();
        server.start(5555);
    }
}
