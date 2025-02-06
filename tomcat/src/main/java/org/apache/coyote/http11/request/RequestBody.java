package org.apache.coyote.http11.request;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestBody {

    private static final Logger log = LoggerFactory.getLogger(RequestBody.class);

    private final Map<String, String> values;
    
    public RequestBody(String requestBody) {
        log.info("\n{}", requestBody);
        HashMap<String, String> requestBodyMap = new HashMap<>();
        String content = trimBraces(requestBody);
        String[] splitContents = content.split(",");
        for (String entry : splitContents) {
            entry = entry.trim().replaceAll("\"","");
            String[] splitEntry = entry.split(":");
            requestBodyMap.put(splitEntry[0].trim(),splitEntry[1].trim());
        }
        this.values = requestBodyMap;
    }

    private String trimBraces(String content){
        return content.substring(1, content.length() - 2);
    }

    public String getValue(String key) {
        return values.get(key);
    }
}
