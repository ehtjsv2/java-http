package org.apache.coyote.http11.request;


public class RequestLine {

    private static final String REQUEST_LINE_SEPARATOR = " ";
    private final Method method;
    private final Path path;

    public RequestLine(String requestLine) {
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

    public String getResourceContentType() {
        return path.getResourceContentType();
    }

    public boolean isGetMethod() {
        return method == Method.GET;
    }

    public boolean isPostMethod() {
        return method == Method.POST;
    }
}
