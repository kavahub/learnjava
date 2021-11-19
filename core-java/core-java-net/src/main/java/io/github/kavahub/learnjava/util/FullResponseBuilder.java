package io.github.kavahub.learnjava.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FullResponseBuilder {

    public String getFullResponse(HttpURLConnection con) throws IOException {
        StringBuilder fullResponseBuilder = new StringBuilder();

        Consumer<Entry<String, List<String>>> ENTRY_CONSUMER = (entry) -> {
            fullResponseBuilder.append(entry.getKey()).append(": ");

            List<String> headerValues = entry.getValue();
            Iterator<String> it = headerValues.iterator();
            if (it.hasNext()) {
                fullResponseBuilder.append(it.next());

                while (it.hasNext()) {
                    fullResponseBuilder.append(", ").append(it.next());
                }
            }

            fullResponseBuilder.append("\n");
        };

        fullResponseBuilder.append(con.getURL()).append("\n").append(con.getRequestMethod()).append("\n")
                .append(con.getResponseCode()).append(" ").append(con.getResponseMessage()).append("\n");

        // 会抛出异常：Already connected 。在请求之后不能读取请求参数 
        //fullResponseBuilder.append("***** Request *****").append("\n");
        //con.getRequestProperties().entrySet().stream().filter(entry -> entry.getKey() != null).forEach(ENTRY_CONSUMER);
        fullResponseBuilder.append("***** Headers *****").append("\n");
        con.getHeaderFields().entrySet().stream().filter(entry -> entry.getKey() != null).forEach(ENTRY_CONSUMER);

        Reader streamReader = null;

        if (con.getResponseCode() > 299) {
            streamReader = new InputStreamReader(con.getErrorStream());
        } else {
            streamReader = new InputStreamReader(con.getInputStream());
        }

        BufferedReader in = new BufferedReader(streamReader);
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();

        fullResponseBuilder.append("***** Response *****").append("\n").append(content);

        return fullResponseBuilder.toString();
    }
}
