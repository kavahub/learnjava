package io.github.kavahub.learnjava.udp;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

/**
 * 
 * {@link DatagramChannel} 构建器
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class DatagramChannelBuilder {
    private DatagramChannel channel;

    public static DatagramChannelBuilder custom() {
        return new DatagramChannelBuilder();
    }

    public DatagramChannelBuilder open() throws IOException {
        channel =  DatagramChannel.open();
        return this;
    }
    
    public DatagramChannelBuilder bind(SocketAddress local) throws IOException {
        if (this.channel == null) {
            throw new RuntimeException("请先先调用open方法");
        }

        if (local != null) {
            channel.bind(local);
        } 
        return this;
    }

    public DatagramChannel build() {
        return this.channel;
    }
}
