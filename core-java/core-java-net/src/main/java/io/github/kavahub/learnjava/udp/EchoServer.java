package io.github.kavahub.learnjava.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class EchoServer extends Thread {
    protected DatagramSocket socket = null;
    protected boolean running;
    protected byte[] buf = new byte[256];

    public EchoServer() throws IOException {
        socket = new DatagramSocket(4445);
    }

    public void run() {
        System.out.println("UDP Server is running");
        running = true;

        while (running) {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                socket.send(packet);
                if (received.equals("end")) {
                    // 先响应客户端，然后关闭，以免客户端一直等待响应（客户端没有设置超时的情况下）
                    running = false;
                }
                
                System.out.println("Received message: " + received);
            } catch (IOException e) {
                e.printStackTrace();
                running = false;
            }
        }

        socket.close();
        System.out.println("UDP Server is closed ");
    }
    
}
