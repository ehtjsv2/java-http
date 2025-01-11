package org.apache.catalina.servletcontainer;

import org.apache.coyote.http11.request.Http11Request;
import org.apache.coyote.http11.response.Http11Response;

public abstract class Http11Servlet implements Servlet{

    @Override
    public void service(Http11Request http11Request, Http11Response http11Response){
        if(http11Request.isGetMethod()){
            doGet(http11Request,http11Response);
        }
        if(http11Request.isPostMethod()){
            doPost(http11Request,http11Response);
        }
    }

    protected abstract void doGet(Http11Request http11Request, Http11Response http11Response);
    protected abstract void doPost(Http11Request http11Request, Http11Response http11Response);
}
