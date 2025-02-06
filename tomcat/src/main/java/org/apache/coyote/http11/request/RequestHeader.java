package org.apache.coyote.http11.request;

import java.util.HashMap;
import java.util.Map;
import org.apache.coyote.http11.Http11Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHeader {

    private final Map<String, String> values;

    public RequestHeader(String requestHeader) {
        HashMap<String, String> headerMap = new HashMap<>();
        String[] splitHeader = requestHeader.split("\r\n");
        for (String header : splitHeader) {
            String[] splitEntry = header.split(": ");
            System.out.println(splitEntry[0]);
            headerMap.put(splitEntry[0], splitEntry[1].trim());
        }
        this.values = headerMap;
    }

    public String getValue(String key) {
        return values.get(key);
    }

    public int getContentLength() {
        return Integer.parseInt(values.get("Content-Length"));
    }

    public boolean isJsonContentType() {
        if (existContentType()) {
            return getContentType() == ContentType.JSON;
        }
        return false;
    }

    private boolean existContentType() {
        return values.get("Content-Type") != null;
    }

    private ContentType getContentType() {
        return ContentType.fromHttpFormat(values.get("Content-Type"));
    }
}
