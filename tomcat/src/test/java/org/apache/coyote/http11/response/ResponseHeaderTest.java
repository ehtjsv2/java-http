package org.apache.coyote.http11.response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ResponseHeaderTest {

    @DisplayName("쿠키를 설정할 수 있다.")
    @Test
    void addCookie() {
        // given
        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.addCookie("JSESSIONID", "656cef62-e3c4-40bc-a8df-94732920ed46");
        responseHeader.addCookie("COOKIE-2", "cookie-2-value");

        // when - then
        assertThat(responseHeader.getCookie("JSESSIONID")).isEqualTo("656cef62-e3c4-40bc-a8df-94732920ed46");
    }
}
