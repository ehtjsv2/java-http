package org.apache.coyote.http11;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.coyote.http11.request.Http11Request;
import org.apache.coyote.http11.response.Http11Response;
import org.apache.coyote.http11.response.Http11ResponseBuilder;

public class Http11StaticResourceProcessor {

    private static final String DEFAULT_RESPONSE_BODY = "Hello world!";

    public Http11Response process(Http11Request http11Request) {

        Http11ResponseBuilder http11ResponseBuilder = new Http11ResponseBuilder();
        if(http11Request.isDefaultPath()){
            http11ResponseBuilder.body(DEFAULT_RESPONSE_BODY);
        }

        Http11Response http11Response = null;

        try {
            URL resource = getClass().getClassLoader().getResource(http11Request.getPath());
            String body = new String(Files.readAllBytes(new File(resource.getFile()).toPath()));
            System.out.println(body.length());
            http11Response = new Http11ResponseBuilder()
                    .statusCode(StatusCode.OK)
                    .contentType("text/html;charset=utf-8")
                    .body(body)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return http11Response;
    }
}
