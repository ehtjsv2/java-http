package org.apache.coyote.http11.request;

public enum Extension {

    HTML("text/html"),
    CSS("text/css"),
    JS("text/javascript");

    private final String contentType;

    Extension(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }
}
