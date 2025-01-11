package org.apache.catalina.servletcontainer;

import org.apache.coyote.http11.request.Http11Request;
import org.apache.coyote.http11.response.Http11Response;

public interface Servlet {

    void service(Http11Request http11Request, Http11Response http11Response);
}
