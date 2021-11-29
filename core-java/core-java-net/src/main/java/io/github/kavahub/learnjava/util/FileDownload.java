package io.github.kavahub.learnjava.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.ExecutionException;

import org.apache.commons.io.FileUtils;
import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.HttpResponseBodyPart;
import org.asynchttpclient.Response;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * 文件下载
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
@UtilityClass
public class FileDownload {
    public void downloadWithJavaIO(String url, String localFilename) throws IOException {
        log.info("Download file with JavaIO from {}", url);
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream()); FileOutputStream fileOutputStream = new FileOutputStream(localFilename)) {

            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        }
    }

    public void downloadWithJava7IO(String url, String localFilename) throws IOException {
        log.info("Download file with Java7IO from {}", url);
        try (InputStream in = new URL(url).openStream()) {
            Files.copy(in, Paths.get(localFilename), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public void downloadWithJavaNIO(String fileURL, String localFilename) throws IOException {
        log.info("Download file with JavaNIO from {}", fileURL);
        URL url = new URL(fileURL);
        try (ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream()); 
            FileOutputStream fileOutputStream = new FileOutputStream(localFilename); FileChannel fileChannel = fileOutputStream.getChannel()) {

            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            fileOutputStream.close();
        }
    }

    public void downloadWithApacheCommons(String url, String localFilename) throws IOException {
        log.info("Download file with ApacheCommons from {}", url);
        int CONNECT_TIMEOUT = 10000;
        int READ_TIMEOUT = 10000;

        FileUtils.copyURLToFile(new URL(url), new File(localFilename), CONNECT_TIMEOUT, READ_TIMEOUT);
    }

    public void downloadWithAHC(String url, String localFilename) throws InterruptedException, ExecutionException, IOException {
        log.info("Download file with AHC from {}", url);
        FileOutputStream stream = new FileOutputStream(localFilename);
        AsyncHttpClient client = Dsl.asyncHttpClient();
        try {
            client.prepareGet(url)
                .execute(new AsyncCompletionHandler<FileOutputStream>() {

                    @Override
                    public State onBodyPartReceived(HttpResponseBodyPart bodyPart) throws Exception {
                        stream.getChannel()
                            .write(bodyPart.getBodyByteBuffer());
                        return State.CONTINUE;
                    }

                    @Override
                    public FileOutputStream onCompleted(Response response) throws Exception {
                        return stream;
                    }
                })
                .get();
        } finally {
            stream.getChannel().close();
            client.close();
        }
    }    
}
