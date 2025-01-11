package org.apache.coyote.http11;

import org.apache.coyote.FileLoader;
import org.apache.coyote.http11.request.Extension;
import org.apache.coyote.http11.request.Http11Request;
import org.apache.coyote.http11.response.Http11Response;
import org.apache.coyote.http11.response.Http11ResponseBuilder;

public class Http11StaticResourceProcessor {

    private static final String DEFAULT_RESPONSE_BODY = "Hello world!";
    private static final String STATIC_RESOURCE_PREFIX_PATH = "static";

    public Http11Response process(Http11Request http11Request) {
        Http11ResponseBuilder http11ResponseBuilder = new Http11ResponseBuilder();
        if (http11Request.isDefaultPath()) {
            return http11ResponseBuilder
                    .statusCode(StatusCode.OK)
                    .contentType(Extension.HTML.getContentType())
                    .body(DEFAULT_RESPONSE_BODY)
                    .build();
        } else {
            return http11ResponseBuilder
                    .statusCode(StatusCode.OK)
                    .contentType(http11Request.getResourceContentType())
                    .body(FileLoader.readResourceAllLine(STATIC_RESOURCE_PREFIX_PATH + http11Request.getPath()))
                    .build();
        }
    }
}
