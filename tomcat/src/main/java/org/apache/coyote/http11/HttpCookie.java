package org.apache.coyote.http11;

public class HttpCookie {

    private final String name;
    private final String value;

    public HttpCookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String toHttpForm() {
        return name + "=" + value;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
