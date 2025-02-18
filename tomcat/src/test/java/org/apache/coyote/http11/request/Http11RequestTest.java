package org.apache.coyote.http11.request;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.coyote.http11.session.Session;
import org.apache.coyote.http11.session.SessionManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Http11RequestTest {

    @DisplayName("JSession을 받아올 수 있다.")
    @Test
    void getJSession() {
        // given
        RequestLine requestLine = new RequestLine("GET / HTTP/1.1");
        String input = String.join("\r\n",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "Cookie: tasty_cookie=strawberry; JSESSIONID=656cef62-e3c4-40bc-a8df-94732920ed46 ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 25");
        RequestHeader requestHeader = new RequestHeader(input);
        Http11Request http11Request = new Http11Request(requestLine, requestHeader, null);

        SessionManager.getInstance().add(new Session("656cef62-e3c4-40bc-a8df-94732920ed46"));

        // when
        Session session = http11Request.getSession(true);

        // then
        assertThat(session.getId()).isEqualTo("656cef62-e3c4-40bc-a8df-94732920ed46");
    }

    @DisplayName("세션이 존재하지않으면 create옵션에 따라 세션을 생성한다.(true)")
    @Test
    void getSession_true() {
        RequestLine requestLine = new RequestLine("GET / HTTP/1.1");
        String input = String.join("\r\n",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 25");
        RequestHeader requestHeader = new RequestHeader(input);
        Http11Request http11Request = new Http11Request(requestLine, requestHeader, null);

        Session session = http11Request.getSession(true);

        assertThat(session).isNotNull();
    }

    @DisplayName("세션이 존재하지않으면 세션을 생성한다.(false)")
    @Test
    void getSession_false() {
        RequestLine requestLine = new RequestLine("GET / HTTP/1.1");
        String input = String.join("\r\n",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 25");
        RequestHeader requestHeader = new RequestHeader(input);
        Http11Request http11Request = new Http11Request(requestLine, requestHeader, null);

        Session session = http11Request.getSession(false);

        assertThat(session).isNull();
    }
}
