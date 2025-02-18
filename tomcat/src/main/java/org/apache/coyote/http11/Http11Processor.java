package org.apache.coyote.http11;

import com.techcourse.exception.UncheckedServletException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.apache.catalina.servletcontainer.HandlerMapping;
import org.apache.catalina.servletcontainer.ServletContainer;
import org.apache.coyote.Processor;
import org.apache.coyote.http11.request.Http11Request;
import org.apache.coyote.http11.request.HttpRequestReader;
import org.apache.coyote.http11.response.Http11Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);

    private final Socket connection;
    private final Http11StaticResourceProcessor http11StaticResourceProcessor;
    private final ServletContainer servletContainer;

    public Http11Processor(Socket connection, HandlerMapping handlerMapping) {
        this.http11StaticResourceProcessor = new Http11StaticResourceProcessor();
        this.servletContainer = new ServletContainer(handlerMapping);
        this.connection = connection;
    }

    @Override
    public void run() {
        log.info("connect host: {}, port: {}", connection.getInetAddress(), connection.getPort());
        process(connection);
    }

    @Override
    public void process(Socket connection) {
        try (InputStream inputStream = connection.getInputStream();
             OutputStream outputStream = connection.getOutputStream()) {

            HttpRequestReader httpRequestReader = new HttpRequestReader();
            Http11Request http11Request = httpRequestReader.createHttp11Request(inputStream);
            Http11Response http11Response = Http11Response.createEmptyResponse();
            if (http11Request.isStaticResourceRequest() || http11Request.isDefaultPath()) {
                http11StaticResourceProcessor.process(http11Request, http11Response);
            } else {
                servletContainer.service(http11Request, http11Response);
            }
            outputStream.write(http11Response.getBytes());
            outputStream.flush();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }
}
