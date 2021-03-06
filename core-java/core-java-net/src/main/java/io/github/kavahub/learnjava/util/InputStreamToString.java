package io.github.kavahub.learnjava.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * {@link InputStream}, {@link String}类型转换
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
@UtilityClass
public class InputStreamToString {
    public String inputStreamToString(InputStream inputStream) {
        final int bufferSize = 8 * 1024;
        byte[] buffer = new byte[bufferSize];
        final StringBuilder builder = new StringBuilder();
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, bufferSize)) {
            while (bufferedInputStream.read(buffer) != -1) {
                builder.append(new String(buffer));
            }
        } catch (IOException ex) {
           log.error("SEVERE", ex);
        }
        return builder.toString();
    }

    public void consumeInputStream(InputStream inputStream) {
        inputStreamToString(inputStream);
    }   
}
