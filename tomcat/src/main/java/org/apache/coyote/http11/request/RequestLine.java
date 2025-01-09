package org.apache.coyote.http11.request;

public class RequestLine {

    private static final String REQUEST_LINE_SEPARATOR = " ";
    private final Method method;
    private final String path;

    public RequestLine(String requestLine) {
        String[] splitRequestLine = requestLine.split(REQUEST_LINE_SEPARATOR);
        this.method = Method.valueOf(splitRequestLine[0]);
        this.path = splitRequestLine[1];
    }

    public boolean isDefaultPath() {
        return path.equals("/");
    }

    public boolean isStaticResourceRequest() {
        if (path.endsWith(".html") || path.endsWith(".css") || path.endsWith(".js")) {
            return true;
        }
        return false;
    }
}
