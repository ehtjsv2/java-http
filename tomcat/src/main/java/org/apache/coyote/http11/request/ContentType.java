package org.apache.coyote.http11.request;

public enum ContentType {

    HTML("text/html"),
    CSS("text/css"),
    JS("text/javascript");

    private final String contentType;

    ContentType(String contentType) {
        this.contentType = contentType;
    }

    public String toHttpResponseFormat() {
        return contentType;
    }
}
