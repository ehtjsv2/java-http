package org.apache.coyote.http11.request;

public enum ContentType {

    HTML("text/html"),
    CSS("text/css"),
    JS("text/javascript"),
    JSON("application/json"),
    X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded");

    private final String contentType;

    ContentType(String contentType) {
        this.contentType = contentType;
    }

    public static ContentType fromHttpFormat(String httpFormatContentType) {
        for (ContentType value : values()) {
            if (value.contentType.equals(httpFormatContentType)) {
                return value;
            }
        }
        throw new IllegalArgumentException("지원하는 Http ContentType이 없습니다.");
    }

    public String toHttpResponseFormat() {
        return contentType;
    }
}
