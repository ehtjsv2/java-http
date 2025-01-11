package com.techcourse;

import org.apache.catalina.servletcontainer.Servlet;

public enum HandlerMappingConfig {
    LOGIN("/login", new LoginServlet());

    private final String requestPath;
    private final Servlet mappingServlet;

    HandlerMappingConfig(String requestPath, Servlet mappingServlet) {
        this.requestPath = requestPath;
        this.mappingServlet = mappingServlet;
    }

    public static Servlet getServlet(String requestPath) {
        HandlerMappingConfig[] values = values();
        for (HandlerMappingConfig value : values) {
            if (value.requestPath.equals(requestPath)) {
                return value.mappingServlet;
            }
        }
        throw new IllegalArgumentException("요청과 일치하는 Servlet이 존재하지 않습니다.");
    }
}
