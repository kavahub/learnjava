package io.github.kavahub.learnjava.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLConnection;

import lombok.experimental.UtilityClass;

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
