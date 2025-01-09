package org.apache.coyote.http11;

public class Http11Response {

    private static final String httpVersion = "HTTP/1.1";
    private final StatusCode statusCode;
    private final String contentType;
    private final String body;

    public Http11Response(StatusCode statusCode, String contentType, String body) {
        this.statusCode = statusCode;
        this.contentType = contentType;
        this.body = body;
    }

    public byte[] getBytes() {
        return String.join("\r\n",
                        getStatusLine(),
                        getHeader(),
                        "", body)
                .getBytes();
    }

    private String getStatusLine() {
        return String.join(" ", httpVersion, statusCode.name(), String.valueOf(statusCode.getCodeNumber()));
    }

    private String getHeader() {
        return String.join("\r\n",
                "Content-Type: " + contentType,
                "Content-Length: " + body.getBytes().length
        );

    }
}
