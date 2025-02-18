package org.apache.coyote.http11.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

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

    @DisplayName("Content Type을 얻을 수 있다.")
    @Test
    void getContentType() {
        String input = String.join("\r\n",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 25");
        RequestHeader requestHeader = new RequestHeader(input);

        assertThat(requestHeader.getValue("Content-Type")).isEqualTo("text/html;charset=utf-8");
    }

    @DisplayName("cookie를 얻을 수 있다.")
    @Test
    void getCookie() {
        String input = String.join("\r\n",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "Cookie: tasty_cookie=strawberry; JSESSIONID=656cef62-e3c4-40bc-a8df-94732920ed46 ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 25");
        RequestHeader requestHeader = new RequestHeader(input);

        assertThat(requestHeader.getCookie("JSESSIONID")).isEqualTo("656cef62-e3c4-40bc-a8df-94732920ed46");
    }

    @DisplayName("cookie key가 존재하지 않으면 null을 반환한다.")
    @Test
    void getCookie_null_when_has_not_key() {
        String input = String.join("\r\n",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "Cookie: tasty_cookie=strawberry",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 25");
        RequestHeader requestHeader = new RequestHeader(input);

        assertThat(requestHeader.getCookie("JSESSIONID")).isNull();
    }

    @DisplayName("cookie가 단 한개도 존재하지 않으면 null을 반환한다.")
    @Test
    void getCookie_null_when_cookie_has_any_cookie() {
        String input = String.join("\r\n",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 25");
        RequestHeader requestHeader = new RequestHeader(input);

        assertThat(requestHeader.getCookie("JSESSIONID")).isNull();
    }

    @DisplayName("cookie가 단 한개도 존재하지 않을 때 setCookie()를 하면 Cookie를 생성한다.")
    @Test
    void create_cookie_when_setCookie_with_no_cookie_status() {
        String input = String.join("\r\n",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 25");
        RequestHeader requestHeader = new RequestHeader(input);

        // when
        requestHeader.setCookie("JSESSIONID", "testValue");

        // then
        assertThat(requestHeader.getCookie("JSESSIONID")).isNotNull();
    }

    @DisplayName("쿠키는 두개이상 존재 가능하다.")
    @Test
    void can_set_cookie_when_already_has_cookie() {
        String input = String.join("\r\n",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "Cookie: tasty_cookie=strawberry",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 25");
        RequestHeader requestHeader = new RequestHeader(input);

        // when
        requestHeader.setCookie("JSESSIONID", "testValue");

        // then
        assertAll(
                () -> assertThat(requestHeader.getCookie("tasty_cookie")).isNotNull(),
                () -> assertThat(requestHeader.getCookie("JSESSIONID")).isNotNull()
        );

    }
}
