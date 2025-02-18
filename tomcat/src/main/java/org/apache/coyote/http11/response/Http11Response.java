package org.apache.coyote.http11.response;

import org.apache.coyote.http11.StatusCode;
import org.apache.coyote.http11.request.ContentType;

public class Http11Response {

    private static final String httpVersion = "HTTP/1.1";

    private StatusCode statusCode;
    private final ResponseHeader responseHeader;
    private String body;

    private Http11Response(StatusCode statusCode, ResponseHeader responseHeader, String body) {
        this.statusCode = statusCode;
        this.responseHeader = responseHeader;
        this.body = body;
    }

    public static Http11Response createEmptyResponse() {
        return new Http11Response(null, new ResponseHeader(), null);
    }

    public void sendRedirection(String location) {
        responseHeader.setLocation(location);
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public void setContentType(ContentType contentType) {
        responseHeader.setContentType(contentType);
    }

    public void setBody(String body) {
        this.body = body;
    }

    private String getStatusLine() {
        return String.join(" ", httpVersion, String.valueOf(statusCode.getCodeNumber()), statusCode.name());
    }

    public String getBody() {
        return body;
    }

    public byte[] getBytes() {
        if (body == null) {
            return (String.join(
                    " \r\n",
                    getStatusLine(),
                    responseHeader.toHttpResponseFormat(),
                    "\r\n")).getBytes();
        }
        return (String.join(
                " \r\n",
                getStatusLine(),
                responseHeader.toHttpResponseFormat(body.length()),
                "\r\n")
                + body)
                .getBytes();
    }

    public void addCookie(String key, String value) {
        responseHeader.addCookie(key, value);
    }

    public String getHeaderValue(String key) {
        return responseHeader.getValue(key);
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }
}
