package com.techcourse;

import com.techcourse.db.InMemoryUserRepository;
import com.techcourse.model.User;
import org.apache.catalina.servletcontainer.Http11Servlet;
import org.apache.coyote.FileLoader;
import org.apache.coyote.http11.StatusCode;
import org.apache.coyote.http11.request.ContentType;
import org.apache.coyote.http11.request.Http11Request;
import org.apache.coyote.http11.response.Http11Response;
import org.apache.coyote.http11.session.Session;

public class LoginServlet extends Http11Servlet {

    @Override
    protected void doGet(Http11Request http11Request, Http11Response http11Response) {
        Session session = http11Request.getSession(true);
        User user = (User) session.getAttribute("user");
        if (user == null) {
            getLoginPage(http11Response);
            return;
        }
        InMemoryUserRepository.findByAccount(user.getAccount())
                .ifPresentOrElse(
                        (user1) -> redirectIndexPage(http11Response),
                        () -> getLoginPage(http11Response)
                );
    }

    @Override
    protected void doPost(Http11Request http11Request, Http11Response http11Response) {
        String account = http11Request.getBodyValue("account");
        String password = http11Request.getBodyValue("password");
        InMemoryUserRepository.findByAccount(account)
                .filter(user -> user.checkPassword(password))
                .ifPresentOrElse(
                        (user) -> processLoginSuccess(user, http11Request, http11Response),
                        () -> processLoginFail(http11Response)
                );
    }

    private void redirectIndexPage(Http11Response http11Response) {
        http11Response.setStatusCode(StatusCode.FOUND);
        http11Response.setContentType(ContentType.HTML);
        http11Response.sendRedirection("/index.html");
    }

    private void getLoginPage(Http11Response http11Response) {
        http11Response.setStatusCode(StatusCode.OK);
        http11Response.setContentType(ContentType.HTML);
        http11Response.setBody(FileLoader.readStaticResourceAllLine("login.html"));
    }

    private void processLoginSuccess(User user, Http11Request http11Request, Http11Response http11Response) {
        Session session = http11Request.getSession(true);
        session.setAttribute("user", user);
        http11Response.addCookie("JSESSIONID", session.getId());
        redirectIndexPage(http11Response);
    }

    private void processLoginFail(Http11Response http11Response) {
        http11Response.setStatusCode(StatusCode.FOUND);
        http11Response.sendRedirection("/401.html");
    }
}
