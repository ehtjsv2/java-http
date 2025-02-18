package org.apache.coyote.http11.request;

import java.util.UUID;
import org.apache.coyote.http11.session.Session;
import org.apache.coyote.http11.session.SessionManager;

public class Http11Request {

    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;

    public Http11Request(RequestLine requestLine, RequestHeader requestHeader, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public boolean isDefaultPath() {
        return requestLine.isDefaultPath();
    }

    public boolean isStaticResourceRequest() {
        return requestLine.isStaticResourceRequest();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public ContentType getResourceContentType() {
        return requestLine.getResourceContentType();
    }

    public boolean isGetMethod() {
        return requestLine.isGetMethod();
    }

    public boolean isPostMethod() {
        return requestLine.isPostMethod();
    }

    public String getQueryValue(String queryKey) {
        return requestLine.getQueryValue(queryKey);
    }

    public String getCookie(String key) {
        return requestHeader.getCookie(key);
    }

    public String getBodyValue(String key) {
        return requestBody.getValue(key);
    }

    // 요청에 대한 세션이 존재하지 않으면 create유무에 따라 생성합니다.
    public Session getSession(boolean create) {
        SessionManager sessionManager = SessionManager.getInstance();
        Session session = sessionManager.findSession(getCookie("JSESSIONID"));
        if (session == null && create == true) {
            String uuid = UUID.randomUUID().toString();
            Session jSession = new Session(uuid);
            requestHeader.setCookie("JESSIONID", uuid);
            sessionManager.add(jSession);
            return jSession;
        }
        return session;
    }
}
