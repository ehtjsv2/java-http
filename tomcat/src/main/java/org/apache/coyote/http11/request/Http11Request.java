package org.apache.coyote.http11.request;

public class Http11Request {

    private final RequestLine requestLine;

    public Http11Request(String request){
        requestLine = new RequestLine(request);
    }

    public boolean isDefaultPath() {
        return requestLine.isDefaultPath();
    }

    public boolean isStaticResourceRequest(){
        return requestLine.isStaticResourceRequest();
    }

    public String getPath() {
        return requestLine.getPath();
    }
}
