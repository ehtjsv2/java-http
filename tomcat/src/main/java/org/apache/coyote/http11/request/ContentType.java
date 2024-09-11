package org.apache.coyote.http11.request;

public enum ContentType {
    APPLICATION_JSON,
    APPLICATION_X_WWW_FORM_URLENCODED,
    ;

    public static ContentType toContentType(String contentType) {
        if (contentType.equals("application/x-www-form-urlencoded")) {
            return APPLICATION_X_WWW_FORM_URLENCODED;
        }
        if (contentType.equals("application/json")) {
            return APPLICATION_JSON;
        }
        throw new IllegalArgumentException("처리되지 않은 contentType입니다.");
    }

    public boolean isFormUrlEncoded() {
        return this == APPLICATION_X_WWW_FORM_URLENCODED;
    }
}
