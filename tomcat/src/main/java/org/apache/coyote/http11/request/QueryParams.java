package org.apache.coyote.http11.request;

import java.util.HashMap;
import java.util.Map;

public class QueryParams {

    Map<String, String> queryValues;

    public QueryParams(String queryValues) {
        HashMap<String, String> queryParams = new HashMap<>();
        for (String queryEntity : queryValues.split("&")) {
            String[] split = queryEntity.split("=");
            queryParams.put(split[0], split[1]);
        }
        this.queryValues = queryParams;
    }

    public String getValue(String key) {
        return queryValues.get(key);
    }
}
