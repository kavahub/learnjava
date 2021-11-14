package io.github.kavahub.learnjava.server.udp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatagramServer {
    private DatagramChannel server;

    public void start() throws IOException {
        InetSocketAddress address = new InetSocketAddress("localhost", 7001);
        server = DatagramChannelBuilder.custom().open().bind(address).build();
        
        log.info("Server started at #" + address);
        
        receiveMessage();
    }
    
    public void receiveMessage() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while(true) {
            SocketAddress remoteAdd = server.receive(buffer);
            String message = extractMessage(buffer);
            
            log.info("Client at #" + remoteAdd + "  sent: " + message);
            buffer.clear();
        }
        
    }
    
    private String extractMessage(ByteBuffer buffer) {
        buffer.flip();

        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        
        String msg = new String(bytes);
        
        return msg;
    }

    public static void main(String[] args) throws IOException {
        DatagramServer server = new DatagramServer();
        server.start();
    }    
}
