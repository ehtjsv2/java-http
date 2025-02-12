package org.apache.coyote.http11.request;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHeader {

    private static final Logger log = LoggerFactory.getLogger(RequestHeader.class);

    private final Map<String, String> values;

    public RequestHeader(String requestHeader) {
        log.info("\n{}", requestHeader);
        HashMap<String, String> headerMap = new HashMap<>();
        String[] splitHeader = requestHeader.split("\r\n");
        for (String header : splitHeader) {
            String[] splitEntry = header.split(": ");
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

    public boolean existContentType() {
        return values.get("Content-Type") != null;
    }

    public ContentType getContentType() {
        return ContentType.fromHttpFormat(values.get("Content-Type"));
    }

    public String getCookie(String key){
        String cookies = values.get("Cookie");
        String[] splitCookieEntries = cookies.split("; ");
        for (String cookieEntry : splitCookieEntries) {
            String[] splitCookieEntry= cookieEntry.split("=");
            if(splitCookieEntry[0].equals(key)){
                return splitCookieEntry[1].trim();
            }
        }
        return null;
    }
}
