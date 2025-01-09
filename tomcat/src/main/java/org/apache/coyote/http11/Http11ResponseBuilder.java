package org.apache.coyote.http11;

public class Http11ResponseBuilder {

    private StatusCode statusCode;
    private String contentType;
    private String body;

    public Http11ResponseBuilder statusCode(StatusCode statusCode){
        this.statusCode = statusCode;
        return this;
    }

    public Http11ResponseBuilder contentType(String contentType){
        this.contentType = contentType;
        return this;
    }

    public Http11ResponseBuilder body(String body){
        this.body = body;
        return this;
    }

    public Http11Response build(){
        return new Http11Response(statusCode, contentType, body);
    }
}
