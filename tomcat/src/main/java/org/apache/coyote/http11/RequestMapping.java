package org.apache.coyote.http11;

import com.techcourse.Controller;
import com.techcourse.LoginController;
import com.techcourse.RegisterController;
import org.apache.coyote.http11.request.HttpRequest;

public class RequestMapping {
    public Controller getController(HttpRequest request) {
        String requestPath = request.getRequestPath();
        if (requestPath.equals("/login")) {
            return new LoginController();
        }
        if (requestPath.equals("/register")) {
            return new RegisterController();
        }
        throw new IllegalArgumentException("처리되지않은 URI경로입니다.");
    }
}
