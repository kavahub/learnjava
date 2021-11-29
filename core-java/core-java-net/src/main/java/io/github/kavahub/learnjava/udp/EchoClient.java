package io.github.kavahub.learnjava.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 客户端
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
public class EchoClient {
    private DatagramSocket socket;
    private InetAddress address;

    private byte[] buf;

    public EchoClient() {
        try {
            socket = new DatagramSocket();
            socket.setSoTimeout(5000);
            address = InetAddress.getByName("localhost");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String sendEcho(String msg) {
        DatagramPacket packet = null;
        try {
            buf = msg.getBytes();
            packet = new DatagramPacket(buf, buf.length, address, 4445);
            socket.send(packet);
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String received = new String(packet.getData(), 0, packet.getLength());
        return received;
    }

    public void close() {
        socket.close();
        log.info("Client is closed");
    }   

    public static void main(String[] args){
        EchoClient client = new EchoClient();
        client.sendEcho("hello");
        client.close();
    }
}
