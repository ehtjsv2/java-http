package org.apache.coyote.http11;

public enum StatusCode {

    OK(200);

    private final int codeNumber;

    StatusCode(int codeNumber) {
        this.codeNumber = codeNumber;
    }

    public int getCodeNumber() {
        return codeNumber;
    }
}
