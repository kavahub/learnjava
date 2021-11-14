package io.github.kavahub.learnjava.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MulticastEchoServer extends Thread {

    protected MulticastSocket socket = null;
    protected byte[] buf = new byte[256];
    protected InetAddress group = null;
    private String serverName = "";

    public MulticastEchoServer() throws IOException {
        socket = new MulticastSocket(4445);
        socket.setReuseAddress(true);
        group = InetAddress.getByName("230.0.0.0");
        socket.joinGroup(group);

        serverName = this.toString();
    }

    public void run() {
        log.info("Multicast UDP Server is running[{}]", serverName);

        try {
            while (true) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                socket.send(packet);

                if (received.equals("end")) {
                    break;
                }
                log.info("Received message[{}]: {}", serverName, received);
            }
            socket.leaveGroup(group);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
