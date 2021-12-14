package io.github.kavahub.learnjava;

import java.io.File;
import java.util.concurrent.CompletableFuture;

import io.atomix.AtomixReplica;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.copycat.server.storage.StorageLevel;

/**
 * 内嵌式集群启动
 * 
 * @author PinWei Wan
 * @since 1.0.2
 */
public class SingleNodeClusterBootstrap {
    public static void main(String[] args) {
        // 日志存储
        Storage storage = Storage.builder()
                .withDirectory(new File("log"))
                .withStorageLevel(StorageLevel.DISK)
                .build();

        // 定义集群节点
        AtomixReplica replica = AtomixReplica.builder(new Address("localhost", 8700))
                .withStorage(storage)
                .withTransport(new NettyTransport())
                .build();

        // 启动集群节点
        CompletableFuture<AtomixReplica> completableFuture = replica.bootstrap();
        completableFuture.join();
    }
}
