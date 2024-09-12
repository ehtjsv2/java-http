package com.techcourse;

import com.techcourse.db.InMemoryUserRepository;
import com.techcourse.model.User;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Optional;
import org.apache.catalina.Session;
import org.apache.catalina.SessionManager;
import org.apache.coyote.http11.ContentType;
import org.apache.coyote.http11.HttpStatus;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;
import util.ResourceFileLoader;

public class LoginController extends AbstractController {

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) throws Exception {
        processLogin(request.getRequestBody());
    }

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws Exception {
        processLoginPage();
    }

    private void processLogin(Map<String, String> requestBody) throws IOException {
        String account = requestBody.get("account");
        String password = requestBody.get("password");
        Optional<User> optionalUser = InMemoryUserRepository.findByAccount(account);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.checkPassword(password)) {
                loginSuccess(user);
                return;
            }
        }
        loginFail();
    }

    private void processLoginPage() throws IOException {
        HttpResponse httpResponse = HttpResponse.createHttpResponse(HttpStatus.OK);
        httpResponse.setContentType(ContentType.text_html);
        httpResponse.setResponseBody(ResourceFileLoader.loadStaticFileToString("login.html"));
    }

    private void loginSuccess(User user) throws IOException {
        Session session = new Session(user.getId().toString());
        session.setAttribute("user", user);
        SessionManager.getInstance().add(session);
        HttpResponse httpResponse = HttpResponse.createHttpResponse(HttpStatus.FOUND);
        httpResponse.setLocation("http://localhost:8080/");
        httpResponse.setCookie("JSESSIONID=" + user.getId().toString());
    }

    private void loginFail() throws IOException {
        HttpResponse httpResponse = HttpResponse.createHttpResponse(HttpStatus.UNAUTHORIZED);
        httpResponse.setResponseBody(ResourceFileLoader.loadStaticFileToString("401.html"));
    }
}
