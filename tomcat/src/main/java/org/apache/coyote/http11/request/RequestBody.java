package org.apache.coyote.http11.request;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestBody {

    private static final Logger log = LoggerFactory.getLogger(RequestBody.class);

    private final Map<String, String> values;

    public RequestBody(String requestBody, ContentType contentType) {
        log.info("\n{}", requestBody);

        Map<String, String> requestBodyMap = null;
        if (contentType == ContentType.JSON) {
            requestBodyMap = getJsonBodyMap(requestBody);
        } else if (contentType == ContentType.X_WWW_FORM_URLENCODED) {
            requestBodyMap = getUrlencodedBodyMap(requestBody);
        }
        this.values = requestBodyMap;
    }

    private Map<String, String> getJsonBodyMap(String requestBody) {
        HashMap<String, String> requestBodyMap = new HashMap<>();
        String content = trimBraces(requestBody);
        String[] splitContents = content.split(",");
        for (String entry : splitContents) {
            entry = entry.trim().replaceAll("\"", "");
            String[] splitEntry = entry.split(":");
            requestBodyMap.put(splitEntry[0].trim(), splitEntry[1].trim());
        }
        return requestBodyMap;
    }

    private Map<String, String> getUrlencodedBodyMap(String requestBody) {
        HashMap<String, String> requestBodyMap = new HashMap<>();
        for (String entry : requestBody.split("&")) {
            String[] split = entry.split("=");
            requestBodyMap.put(split[0], split[1]);
        }
        return requestBodyMap;
    }

    private String trimBraces(String content) {
        return content.substring(1, content.length() - 2);
    }

    public String getValue(String key) {
        return values.get(key);
    }
}
