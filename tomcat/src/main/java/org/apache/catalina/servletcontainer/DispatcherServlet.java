package org.apache.catalina.servletcontainer;

import org.apache.coyote.http11.request.Http11Request;
import org.apache.coyote.http11.response.Http11Response;

public class DispatcherServlet {

    private final HandlerMapping handlerMapping;

    public DispatcherServlet(HandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    public void service(Http11Request http11Request, Http11Response http11Response) {
        Servlet handlerMappingServlet = handlerMapping.getServlet(http11Request);
        handlerMappingServlet.service(http11Request, http11Response);
    }
}
