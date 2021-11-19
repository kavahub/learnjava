package io.github.kavahub.learnjava.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ParameterStringBuilder {
    public String getParamsString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        final int length = result.length();
        if (length > 0) {
            result.deleteCharAt(length - 1);
        }
        return result.toString();
    }   
}
