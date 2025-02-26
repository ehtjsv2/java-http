package org.apache.coyote.http11.request;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestLine {

    private static final Logger log = LoggerFactory.getLogger(RequestLine.class);

    private static final String REQUEST_LINE_SEPARATOR = " ";
    private final Method method;
    private final Path path;

    public RequestLine(String requestLine) {
        log.info(requestLine);
        String[] splitRequestLine = requestLine.split(REQUEST_LINE_SEPARATOR);
        this.method = Method.valueOf(splitRequestLine[0]);
        this.path = new Path(splitRequestLine[1]);
    }

    public boolean isDefaultPath() {
        return path.isDefaultPath();
    }

    public boolean isStaticResourceRequest() {
        return path.isExistExtension();
    }

    public String getPath() {
        return path.getValue();
    }

    public ContentType getResourceContentType() {
        return path.getResourceContentType();
    }

    public boolean isGetMethod() {
        return method == Method.GET;
    }

    public boolean isPostMethod() {
        return method == Method.POST;
    }

    public String getQueryValue(String queryKey) {
        return path.getQueryValue(queryKey);
    }
}
