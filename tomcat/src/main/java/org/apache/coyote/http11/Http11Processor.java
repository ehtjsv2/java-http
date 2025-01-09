package org.apache.coyote.http11;

import com.techcourse.exception.UncheckedServletException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.coyote.Processor;
import org.apache.coyote.http11.request.Http11Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);
    private static final String DEFAULT_RESPONSE_BODY = "Hello world!";

    private final Socket connection;

    public Http11Processor(final Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        log.info("connect host: {}, port: {}", connection.getInetAddress(), connection.getPort());
        process(connection);
    }

    @Override
    public void process(final Socket connection) {
        try (final var inputStream = connection.getInputStream();
             final var outputStream = connection.getOutputStream()) {

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String requestLine = br.readLine();
            Http11Request http11Request = new Http11Request(requestLine);
            Http11ResponseBuilder http11ResponseBuilder = new Http11ResponseBuilder();
            if(http11Request.isDefaultPath()){
                http11ResponseBuilder.body(DEFAULT_RESPONSE_BODY);
            }
            Http11Response http11Response = new Http11ResponseBuilder()
                    .statusCode(StatusCode.OK)
                    .contentType("text/html;charset=utf-8")
                    .body(DEFAULT_RESPONSE_BODY)
                    .build();

            String response = String.join("\r\n",
                    "HTTP/1.1 200 OK ",
                    "Content-Type: text/html;charset=utf-8 ",
                    "Content-Length: " + DEFAULT_RESPONSE_BODY.getBytes().length + " ",
                    "",
                    DEFAULT_RESPONSE_BODY);

            outputStream.write(response.getBytes());
            outputStream.flush();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }
}
