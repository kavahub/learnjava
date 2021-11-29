package io.github.kavahub.learnjava.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 服务器
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
public class BroadcastingEchoServer extends Thread {

    protected DatagramSocket socket = null;
    protected boolean running;
    protected byte[] buf = new byte[256];
    private String serverName = "";

    public BroadcastingEchoServer() throws IOException {
        // 指定Null很重要，否则Java会自动随机选个可用端口来绑定
        socket = new DatagramSocket(null);
        // 可以允许多个DatagramSocket绑定到相同的IP地址和端口，
        // 那么发送到此IP地址和端口的数据能够被复制到多个DatagramSocket，也就是说能够实现多播的功能
        socket.setReuseAddress(true);
        socket.bind(new InetSocketAddress(4445));
        serverName = this.toString();
    }

    public void run() {
        log.info("Broadcasting UDP Server is running[{}]", serverName);
        running = true;

        while (running) {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                socket.send(packet);

                if (received.equals("end")) {
                    running = false;
                }
                log.info("Received message[{}]: {}", serverName, received);
            } catch (IOException e) {
                e.printStackTrace();
                running = false;
            }
        }
        socket.close();
    }
    
}
