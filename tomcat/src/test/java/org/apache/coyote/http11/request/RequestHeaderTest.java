package org.apache.coyote.http11.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestHeaderTest {

    @DisplayName("String의 Header로 RequestHeader객체를 생성할 수 있다.")
    @Test
    void createRequestHeader() {
        String input = String.join("\r\n",
                "Host: localhost:8080 ",
                "Connection: keep-alive ");

        assertDoesNotThrow(() -> new RequestHeader(input));
    }

    @DisplayName("원하는 header value를 얻을 수 있다.")
    @Test
    void getHeaderValue() {
        String input = String.join("\r\n",
                "Host: localhost:8080 ",
                "Connection: keep-alive ");
        RequestHeader requestHeader = new RequestHeader(input);

        assertThat(requestHeader.getValue("Host")).isEqualTo("localhost:8080");
    }

    @DisplayName("Content Length를 얻을 수 있다.")
    @Test
    void getContentLength() {
        String input = String.join("\r\n",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "Content-Length: 25");
        RequestHeader requestHeader = new RequestHeader(input);

        assertThat(requestHeader.getContentLength()).isEqualTo(25);
    }
}
