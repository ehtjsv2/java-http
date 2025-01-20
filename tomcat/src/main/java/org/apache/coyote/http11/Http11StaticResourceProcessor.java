package org.apache.coyote.http11;

import org.apache.coyote.FileLoader;
import org.apache.coyote.http11.request.ContentType;
import org.apache.coyote.http11.request.Http11Request;
import org.apache.coyote.http11.response.Http11Response;

public class Http11StaticResourceProcessor {

    private static final String DEFAULT_RESPONSE_BODY = "Hello world!";
    private static final String STATIC_RESOURCE_PREFIX_PATH = "static";

    public void process(Http11Request http11Request, Http11Response http11Response) {
        if (http11Request.isDefaultPath()) {
            http11Response.setStatusCode(StatusCode.OK);
            http11Response.setContentType(ContentType.HTML);
            http11Response.setBody(DEFAULT_RESPONSE_BODY);
        } else {
            http11Response.setStatusCode(StatusCode.OK);
            http11Response.setContentType(http11Request.getResourceContentType());
            http11Response.setBody(FileLoader.readResourceAllLine(STATIC_RESOURCE_PREFIX_PATH + http11Request.getPath()));
        }
    }
}
