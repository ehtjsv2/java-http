package org.apache.catalina.servletcontainer;

import org.apache.coyote.http11.request.Http11Request;

public interface HandlerMapping {

    Servlet getServlet(Http11Request http11Request);
}
