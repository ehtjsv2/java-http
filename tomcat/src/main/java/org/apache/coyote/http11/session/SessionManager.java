package org.apache.coyote.http11.session;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {

    private static final Map<String, Session> SESSIONS = new HashMap<>();

    private static SessionManager INSTANCE = new SessionManager();

    public static SessionManager getInstance() {
        return INSTANCE;
    }

    private SessionManager() {

    }

    public void add(Session session) {
        SESSIONS.put(session.getId(), session);
    }

    public Session findSession(String id) {
        return SESSIONS.get(id);
    }

    public void remove(String id) {
        SESSIONS.remove(id);
    }
}
