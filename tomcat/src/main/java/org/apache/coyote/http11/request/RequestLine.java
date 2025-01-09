package org.apache.coyote.http11.request;

import java.nio.file.Path;
import java.nio.file.Paths;

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
}
