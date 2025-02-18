package org.apache.coyote.http11;

public enum StatusCode {

    OK(200),
    FOUND(302),
    UNAUTHORIZED(401);

    private final int codeNumber;

    StatusCode(int codeNumber) {
        this.codeNumber = codeNumber;
    }

    public int getCodeNumber() {
        return codeNumber;
    }
}
