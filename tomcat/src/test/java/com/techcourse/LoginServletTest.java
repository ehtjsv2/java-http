package com.techcourse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.apache.coyote.FileLoader;
import org.apache.coyote.http11.StatusCode;
import org.apache.coyote.http11.request.Http11Request;
import org.apache.coyote.http11.request.RequestLine;
import org.apache.coyote.http11.response.Http11Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LoginServletTest {

    private LoginServlet loginServlet;

    @BeforeEach
    void setUp() {
        loginServlet = new LoginServlet();
    }

    @DisplayName("login get요청시, 로그인페이지를 응답한다.")
    @Test
    void doGet() {
        // given
        Http11Request request = new Http11Request(new RequestLine("GET /login HTTP/1.1 "), null);
        Http11Response response = Http11Response.createEmptyResponse();

        // when
        loginServlet.doGet(request, response);

        // then
        assertThat(response.getBody()).isEqualTo(FileLoader.readResourceAllLine("login.html"));
    }

    @DisplayName("login Post요청시, 로그인 성공하면 index페이지로 리다이렉션한다.")
    @Test
    void doPostRedirection() {
        // given
        Http11Request request = new Http11Request(
                new RequestLine("POST /login?account=gugu&password=password HTTP/1.1 "),
                null
        );
        Http11Response response = Http11Response.createEmptyResponse();

        // when
        loginServlet.doPost(request, response);

        // then
        assertThat(response.getHeaderValue("Location")).isEqualTo("/index.html");
    }

    @DisplayName("login Post요청시, 로그인 성공하면 302 FOUND를 응답한다.")
    @Test
    void doPostStatusCode() {
        // given
        Http11Request request = new Http11Request(
                new RequestLine("POST /login?account=gugu&password=password HTTP/1.1 ")
                , null
        );
        Http11Response response = Http11Response.createEmptyResponse();

        // when
        loginServlet.doPost(request, response);

        // then
        assertThat(response.getStatusCode()).isEqualTo(StatusCode.FOUND);
    }

    @DisplayName("login Post요청시, 로그인 실패하면 401.html 페이지로 리다이렉션한다.")
    @Test
    void doPostRedirectionFail() {
        // given
        Http11Request request = new Http11Request(
                new RequestLine("POST /login?account=gugu&password=x HTTP/1.1 "),
                null
        );
        Http11Response response = Http11Response.createEmptyResponse();

        // when
        loginServlet.doPost(request, response);

        // then
        assertThat(response.getHeaderValue("Location")).isEqualTo("/401.html");
    }

    @DisplayName("login Post요청시, 로그인 실패하면 401 UNAUTHORIZED를 응답한다.")
    @Test
    void doPostStatusCodeFail() {
        // given
        Http11Request request = new Http11Request(
                new RequestLine("POST /login?account=gugu&password=x HTTP/1.1 "),
                null
        );
        Http11Response response = Http11Response.createEmptyResponse();

        // when
        loginServlet.doPost(request, response);

        // then
        assertThat(response.getStatusCode()).isEqualTo(StatusCode.UNAUTHORIZED);
    }
}
