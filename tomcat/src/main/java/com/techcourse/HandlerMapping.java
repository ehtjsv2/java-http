package com.techcourse;

import org.apache.catalina.servletcontainer.Servlet;
import org.apache.coyote.http11.request.Http11Request;

public class HandlerMapping {

    public Servlet getServlet(Http11Request http11Request){
        return HandlerMappingConfig.getServlet(http11Request.getPath());
    }
}
