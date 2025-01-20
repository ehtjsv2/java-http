package org.apache.coyote.http11.response;

import org.apache.coyote.http11.StatusCode;
import org.apache.coyote.http11.request.ContentType;

public class Http11Response {

    private static final String httpVersion = "HTTP/1.1";
    private static final String encodingType = "utf-8";

    private StatusCode statusCode;
    private ContentType contentType;
    private String body;

    private Http11Response(StatusCode statusCode, ContentType contentType, String body) {
        this.statusCode = statusCode;
        this.contentType = contentType;
        this.body = body;
    }

    public static Http11Response createEmptyResponse() {
        return new Http11Response(null, null, null);
    }

    public byte[] getBytes() {
        return (String.join(" \r\n", getStatusLine(), getHeader(), "\r\n") + body).getBytes();
    }

    private String getStatusLine() {
        return String.join(" ", httpVersion, String.valueOf(statusCode.getCodeNumber()), statusCode.name());
    }

    private String getHeader() {
        return String.join(" \r\n",
                "Content-Type: " + String.join(";", contentType.toHttpResponseFormat(), "charset=" + encodingType),
                "Content-Length: " + body.length()
        );

    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }
}
