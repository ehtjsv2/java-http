package org.apache.coyote.http11.request;

public class Http11Request {

    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;

    public Http11Request(RequestLine requestLine, RequestHeader requestHeader, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public boolean isDefaultPath() {
        return requestLine.isDefaultPath();
    }

    public boolean isStaticResourceRequest() {
        return requestLine.isStaticResourceRequest();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public ContentType getResourceContentType() {
        return requestLine.getResourceContentType();
    }

    public boolean isGetMethod() {
        return requestLine.isGetMethod();
    }

    public boolean isPostMethod() {
        return requestLine.isPostMethod();
    }

    public String getQueryValue(String queryKey) {
        return requestLine.getQueryValue(queryKey);
    }

    public String getBodyValue(String key) {
        return requestBody.getValue(key);
    }
}
