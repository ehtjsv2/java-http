package org.apache.coyote.http11;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.apache.coyote.http11.request.Http11Request;
import org.apache.coyote.http11.request.RequestLine;
import org.apache.coyote.http11.response.Http11Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Http11StaticResourceProcessorTest {

    @DisplayName("GET / HTTP/1.1 요청이들어오면 Hello world! 바디를 응답한다.")
    @Test
    void defaultPathResponse() {
        Http11StaticResourceProcessor http11StaticResourceProcessor = new Http11StaticResourceProcessor();
        Http11Response response = Http11Response.createEmptyResponse();
        http11StaticResourceProcessor.process(new Http11Request(new RequestLine("GET / HTTP/1.1"), null, null), response);

        assertThat(response.getBody()).isEqualTo("Hello world!");
    }
}
