package org.apache.coyote.http11;

import com.techcourse.exception.UncheckedServletException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import org.apache.catalina.servletcontainer.DispatcherServlet;
import org.apache.catalina.servletcontainer.HandlerMapping;
import org.apache.coyote.Processor;
import org.apache.coyote.http11.request.Http11Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);

    private final Socket connection;
    private final Http11StaticResourceProcessor http11StaticResourceProcessor;
    private final DispatcherServlet dispatcherServlet;

    public Http11Processor(Socket connection, HandlerMapping handlerMapping) {
        this.http11StaticResourceProcessor = new Http11StaticResourceProcessor();
        this.dispatcherServlet = new DispatcherServlet(handlerMapping);
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
            if (http11Request.isStaticResourceRequest() || http11Request.isDefaultPath()) {
                outputStream.write(http11StaticResourceProcessor.process(http11Request).getBytes());
            } else {

            }
            outputStream.flush();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }
}
