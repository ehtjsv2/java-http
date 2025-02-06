package com.techcourse;

import com.techcourse.db.InMemoryUserRepository;
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
        String account = http11Request.getBodyValue("account");
        String password = http11Request.getBodyValue("password");
        InMemoryUserRepository.findByAccount(account)
                .filter(user -> user.checkPassword(password))
                .ifPresentOrElse(
                        (user) -> processLoginSuccess(http11Response),
                        () -> processLoginFail(http11Response)
                );
    }

    private void processLoginSuccess(Http11Response http11Response) {
        http11Response.setStatusCode(StatusCode.FOUND);
        http11Response.sendRedirection("/index.html");
    }

    private void processLoginFail(Http11Response http11Response) {
        http11Response.setStatusCode(StatusCode.UNAUTHORIZED);
        http11Response.sendRedirection("/401.html");
    }
}
