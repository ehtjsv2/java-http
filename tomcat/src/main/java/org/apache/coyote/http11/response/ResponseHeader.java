package org.apache.coyote.http11.response;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.coyote.http11.request.ContentType;

public class ResponseHeader {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String LOCATION = "Location";

    private static final String DEFAULT_ENCODING_TYPE = "utf-8";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String RESPONSE_HEADER_COOKIE_KEY = "Set-Cookie";

    private final Map<String, String> values;
    private String encodingType;

    public ResponseHeader() {
        this.values = new HashMap<>();
        this.encodingType = DEFAULT_ENCODING_TYPE;
    }

    public void setContentType(ContentType contentType) {
        values.put(CONTENT_TYPE, contentType.toHttpResponseFormat() + ";charset=" + encodingType);
    }

    public void setLocation(String location) {
        values.put(LOCATION, location);
    }

    public String toHttpResponseFormat() {
        StringBuilder sb = new StringBuilder();
        for (Entry<String, String> header : values.entrySet()) {
            sb.append(header.getKey()).append(": ").append(header.getValue()).append(" \r\n");
        }
        return sb.toString();
    }

    public String toHttpResponseFormat(int contentLength) {
        return toHttpResponseFormat() + CONTENT_LENGTH + ": " + contentLength;
    }

    public void addCookie(String key, String value) {
        if (values.containsKey(RESPONSE_HEADER_COOKIE_KEY)) {
            values.put(RESPONSE_HEADER_COOKIE_KEY, values.get(RESPONSE_HEADER_COOKIE_KEY) + "; " + key + "=" + value);
            return;
        }
        values.put(RESPONSE_HEADER_COOKIE_KEY, key + "=" + value);
    }

    public String getCookie(String key){
        String cookies = values.get(RESPONSE_HEADER_COOKIE_KEY);
        String[] splitCookieEntries = cookies.split("; ");
        for (String cookieEntry : splitCookieEntries) {
            String[] splitCookieEntry= cookieEntry.split("=");
            if(splitCookieEntry[0].equals(key)){
                return splitCookieEntry[1].trim();
            }
        }
        return null;
    }

    public String getValue(String key) {
        return values.get(key);
    }
}
