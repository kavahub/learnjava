package io.github.kavahub.learnjava.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.time.Duration;
import java.time.Instant;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * 文件下载，支持断点续传
 */
@Slf4j
@UtilityClass
public class FileResumableDownload {

    /**
     * 下载文件，不支持断点续传
     * 
     * @param downloadUrl
     * @param saveAsFileName
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public long downloadFile(String downloadUrl, String saveAsFileName) throws IOException, URISyntaxException {
        log.info("Download file from {}", downloadUrl);

        File outputFile = new File(saveAsFileName);
        HttpURLConnection downloadFileConnection = (HttpURLConnection)new URI(downloadUrl).toURL().openConnection();
        
        long downloadFileSize = transferDataAndGetBytesDownloaded(downloadFileConnection, outputFile, DownloadProgress.NOOP);
        downloadFileConnection.disconnect();
        return downloadFileSize;
    }

    private long transferDataAndGetBytesDownloaded(URLConnection downloadFileConnection, File outputFile,
            DownloadProgress progress) throws IOException {
        progress.start();
        long bytesDownloaded = 0;
        try (InputStream is = downloadFileConnection.getInputStream();
                OutputStream os = new FileOutputStream(outputFile, true)) {

            byte[] buffer = new byte[1024];

            int bytesCount;
            while ((bytesCount = is.read(buffer)) > 0) {
                os.write(buffer, 0, bytesCount);
                bytesDownloaded += bytesCount;
                progress.apply(bytesDownloaded);
            }
        }

        log.info("Download file finished[Total size: {}]", bytesDownloaded);
        return bytesDownloaded;
    }

    /**
     * 文件下载，支持断点续传
     * 
     * @param downloadUrl
     * @param saveAsFileName
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public long downloadFileWithResume(String downloadUrl, String saveAsFileName)
            throws IOException, URISyntaxException {
        log.info("Resumable download file from {}", downloadUrl);
        long existingFileLength = 0L;
        File outputFile = new File(saveAsFileName);
        if (outputFile.exists()) {
            existingFileLength = outputFile.length();
        }

        long actualFileLength = getContentLength(downloadUrl);
        if (existingFileLength >= actualFileLength) {
            log.info("Download file finished [Total size: {}]", existingFileLength);
            return existingFileLength;
        }

        HttpURLConnection downloadFileConnection = (HttpURLConnection)new URI(downloadUrl).toURL().openConnection();

        if (existingFileLength > 0) {
            // 断点续传
            log.info("Resume from break-point: {}", existingFileLength);
            downloadFileConnection.setRequestProperty("Range", "bytes=" + existingFileLength + "-" + actualFileLength);
        }

        DownloadProgress progress = new DownloadProgress(actualFileLength);
        long downloadFileSize = transferDataAndGetBytesDownloaded(downloadFileConnection, outputFile, progress);

        downloadFileConnection.disconnect();
        return downloadFileSize;
    }

    private long getContentLength(String downloadUrl) throws MalformedURLException, IOException, URISyntaxException {
        HttpURLConnection connect = (HttpURLConnection) new URI(downloadUrl).toURL().openConnection();
        connect.setRequestMethod("HEAD");
        long fileSize = connect.getContentLengthLong();

        connect.disconnect();
        return fileSize;
    }

    public static class DownloadProgress {
        public final static DownloadProgress NOOP = new DownloadProgress(-1);

        private final long actualSize;

        private Instant startTime;
        private long currentSizeCopy;

        public DownloadProgress(long actualSize) {
            this.actualSize = actualSize;
        }

        public void start() {
            this.startTime = Instant.now();
        }

        public void apply(final long currentSize) {
            if (this.actualSize <= 0) {
                return;
            }

            if (currentSize - this.currentSizeCopy < 5120) {
                return;
            }

            this.currentSizeCopy = currentSize;
            double percent = currentSize * 100d / actualSize;

            double wasteTime = Duration.between(this.startTime, Instant.now()).toMillis();
            double spend = currentSize / wasteTime;

            log.info("Download in progress [Total(b):{}][Current(b):{}][Percent(%):{}][Spend(kb/s):{}]", this.actualSize, currentSize,
                    String.format("%.1f", percent), String.format("%.2f", spend));
        }
    }
}
