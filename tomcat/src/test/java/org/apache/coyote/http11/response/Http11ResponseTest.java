package org.apache.coyote.http11.response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import org.apache.coyote.http11.StatusCode;
import org.apache.coyote.http11.request.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Http11ResponseTest {

    @DisplayName("Body가 null일 경우, body와 contentLength가 없다")
    @Test
    void getBytesWithNoBody() {
        // given
        String expectedBody = String.join(" \r\n",
                "HTTP/1.1 200 OK",
                "",
                "\r\n");
        Http11Response response = Http11Response.createEmptyResponse();
        response.setStatusCode(StatusCode.OK);

        // when - then
        assertThat(new String(response.getBytes())).isEqualTo(expectedBody);
    }

    @DisplayName("Body가 있을 경우, body와 contentLength가 있다")
    @Test
    void getBytesWithBody() {
        // given
        String expectedBody = String.join(" \r\n",
                "HTTP/1.1 200 OK",
                "Content-Type: " +ContentType.JSON.toHttpResponseFormat()+";charset=utf-8",
                "Content-Length: 5",
                "\r\n" +"hello");
        Http11Response response = Http11Response.createEmptyResponse();
        response.setStatusCode(StatusCode.OK);
        response.setContentType(ContentType.JSON);
        response.setBody("hello");

        // when - then
        assertThat(new String(response.getBytes())).isEqualTo(expectedBody);
    }
}
