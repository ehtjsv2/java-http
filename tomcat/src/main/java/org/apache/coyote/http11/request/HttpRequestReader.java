package org.apache.coyote.http11.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestReader {

    private static final Logger log = LoggerFactory.getLogger(HttpRequestReader.class);

    public HttpRequestReader() {

    }

    public Http11Request createHttp11Request(InputStream inputStream) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            RequestLine requestLine = new RequestLine(br.readLine());
            RequestHeader requestHeader = getHeader(br);
            RequestBody requestBody = getBody(br, requestHeader);
            return new Http11Request(requestLine, requestHeader, requestBody);
        } catch (IOException e) {
            throw new IllegalArgumentException("잘못된 Http11Request 형식입니다", e);
        }
    }

    private RequestHeader getHeader(BufferedReader br) throws IOException {
        String header = "";
        String line;
        while (!(line = br.readLine()).isEmpty()) {
            header += line + System.lineSeparator();
        }
        return new RequestHeader(header);
    }

    private RequestBody getBody(BufferedReader br, RequestHeader requestHeader) throws IOException {
        if (!requestHeader.existContentType()) {
            return null;
        }
        char[] requestBodyBuffer = new char[requestHeader.getContentLength()];
        br.read(requestBodyBuffer, 0, requestHeader.getContentLength());
        return new RequestBody(new String(requestBodyBuffer), requestHeader.getContentType());
    }
}
