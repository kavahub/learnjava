package io.github.kavahub.learnjava;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.function.BiConsumer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileReader implements CompletionHandler<Integer, AsynchronousFileChannel> {
    private final Path file;
    private final BiConsumer<ByteBuffer, Integer> consumer;

    private int pos = 0;
    private ByteBuffer buffer;

    public FileReader(Path file, BiConsumer<ByteBuffer, Integer> consumer) {
        this.file = file;
        this.consumer = consumer;
    }

    @Override
    public void completed(Integer result, AsynchronousFileChannel attachment) {
        // if result is -1 means nothing was read.
        if (result != -1) {
            pos += result;
            buffer.flip();

            this.consumer.accept(buffer, result);

            buffer.clear(); // reset the buffer so you can read more.
        } else {

        }
        
        // initiate another asynchronous read, with this.
        attachment.read(buffer, pos, attachment, this);
        
    }

    @Override
    public void failed(Throwable exc, AsynchronousFileChannel attachment) {
        log.error("FileConsumer error", exc); 
    }

    public void run() throws IOException {
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(file, StandardOpenOption.READ);
        buffer = ByteBuffer.allocate(1024);

        // start off the asynch read.
        channel.read(buffer, pos, channel, this);
    }

    public int getTotal() {
        return pos;
    }
}
