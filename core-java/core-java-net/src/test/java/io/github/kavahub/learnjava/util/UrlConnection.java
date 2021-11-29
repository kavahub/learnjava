package io.github.kavahub.learnjava.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLConnection;

import lombok.experimental.UtilityClass;

/**
 * 
 * Http 连接工具
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class UrlConnection {
    /**
     * 响应结果转换成字符串
     * 
     * @param con
     * @return
     * @throws IOException
     */
    public String contentAsString(URLConnection con) throws IOException {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader 
                = new BufferedReader(new InputStreamReader(con.getInputStream()))){
            while (reader.ready()) {
                builder.append(reader.readLine());
            }
        }
        return builder.toString();
    }    
}
