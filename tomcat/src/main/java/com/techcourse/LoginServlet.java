package com.techcourse;

import org.apache.catalina.servletcontainer.Http11Servlet;
import org.apache.coyote.FileLoader;
import org.apache.coyote.http11.StatusCode;
import org.apache.coyote.http11.request.ContentType;
import org.apache.coyote.http11.request.Http11Request;
import org.apache.coyote.http11.response.Http11Response;

public class LoginServlet extends Http11Servlet {

    @Override
    protected void doGet(Http11Request http11Request, Http11Response http11Response) {
        http11Response.setStatusCode(StatusCode.OK);
        http11Response.setContentType(ContentType.HTML);
        http11Response.setBody(FileLoader.readStaticResourceAllLine("login.html"));
    }

    @Override
    protected void doPost(Http11Request http11Request, Http11Response http11Response) {

    }
}
