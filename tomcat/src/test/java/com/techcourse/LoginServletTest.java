package com.techcourse;

import static org.assertj.core.api.Assertions.assertThat;

import com.techcourse.db.InMemoryUserRepository;
import com.techcourse.model.User;
import java.util.UUID;
import org.apache.coyote.FileLoader;
import org.apache.coyote.http11.StatusCode;
import org.apache.coyote.http11.request.ContentType;
import org.apache.coyote.http11.request.Http11Request;
import org.apache.coyote.http11.request.RequestBody;
import org.apache.coyote.http11.request.RequestHeader;
import org.apache.coyote.http11.request.RequestLine;
import org.apache.coyote.http11.response.Http11Response;
import org.apache.coyote.http11.session.Session;
import org.apache.coyote.http11.session.SessionManager;
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
        Http11Request request = new Http11Request(new RequestLine("GET /login HTTP/1.1 "), null, null);
        Http11Response response = Http11Response.createEmptyResponse();

        // when
        loginServlet.doGet(request, response);

        // then
        assertThat(response.getBody()).isEqualTo(FileLoader.readResourceAllLine("login.html"));
    }

    @DisplayName("login get요청시, userSession이 존재하면 index페이지로 리다이렉트한다.")
    @Test
    void doGet_return_index_page_when_has_user_session() {
        // given
        User testUser = new User(1L, "account", "password", "email");
        InMemoryUserRepository.save(testUser);

        String uuid = UUID.randomUUID().toString();
        Session session = new Session(uuid);
        session.setAttribute("user", testUser);
        SessionManager.getInstance().add(session);

        Http11Request request = new Http11Request(
                new RequestLine("GET /login HTTP/1.1 "),
                new RequestHeader(String.join("\r\n",
                        "Host: localhost:8080 ",
                        "Connection: keep-alive ",
                        "Cookie: JSESSIONID=" + uuid,
                        "Content-Type: text/html;charset=utf-8 ",
                        "Content-Length: 25")),
                null);
        Http11Response response = Http11Response.createEmptyResponse();

        // when
        loginServlet.doGet(request, response);

        // then
        assertThat(response.getHeaderValue("Location")).isEqualTo("/index.html");
    }

    @DisplayName("login Post요청시, 로그인 성공하면 index페이지로 리다이렉션한다.")
    @Test
    void doPostRedirection() {
        // given
        Http11Request request = new Http11Request(
                new RequestLine("POST /login HTTP/1.1 "),
                null,
                new RequestBody("""
                        {
                            "account": "gugu",
                            "password": "password"
                        }
                        """, ContentType.JSON)
        );
        Http11Response response = Http11Response.createEmptyResponse();

        // when
        loginServlet.doPost(request, response);

        // then
        assertThat(response.getHeaderValue("Location")).isEqualTo("/index.html");
    }

    @DisplayName("login Post요청시, 로그인 성공하면 JESSIONID를 부여한다.")
    @Test
    void doPost_give_JESSIONID() {
        // given
        Http11Request request = new Http11Request(
                new RequestLine("POST /login HTTP/1.1 "),
                new RequestHeader(String.join("\r\n",
                        "Host: localhost:8080 ",
                        "Connection: keep-alive ")),
                new RequestBody("""
                        {
                            "account": "gugu",
                            "password": "password"
                        }
                        """, ContentType.JSON)
        );
        Http11Response response = Http11Response.createEmptyResponse();

        // when
        loginServlet.doPost(request, response);

        // then
        assertThat(response.getHeaderValue("Set-Cookie")).contains("JSESSIONID");
    }

    @DisplayName("login Post요청시, 로그인 성공하면 302 FOUND를 응답한다.")
    @Test
    void doPostStatusCode() {
        // given
        Http11Request request = new Http11Request(
                new RequestLine("POST /login HTTP/1.1 "),
                null,
                new RequestBody("""
                        {
                            "account": "gugu",
                            "password": "password"
                        }
                        """, ContentType.JSON)
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
                new RequestLine("POST /login HTTP/1.1 "),
                null,
                new RequestBody("""
                        {
                            "account": "gugu",
                            "password": "x"
                        }
                        """, ContentType.JSON)
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
                new RequestLine("POST /login HTTP/1.1 "),
                null,
                new RequestBody("""
                        {
                            "account": "gugu",
                            "password": "x"
                        }
                        """, ContentType.JSON)
        );
        Http11Response response = Http11Response.createEmptyResponse();

        // when
        loginServlet.doPost(request, response);

        // then
        assertThat(response.getStatusCode()).isEqualTo(StatusCode.FOUND);
    }
}
