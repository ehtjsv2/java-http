package org.apache.coyote.http11.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestBodyTest {


    @DisplayName("Json타입의 Body를 Map으로 변환할 수 있다.")
    @Test
    void createJsonBody() {
        // given
        String input = """
                {
                    "name": "ehtjs",
                    "password": "password"
                }
                """;

        // when
        RequestBody requestBody = new RequestBody(input, ContentType.JSON);

        // then
        assertThat(requestBody.getValue("name")).isEqualTo("ehtjs");
    }

    @DisplayName("UrlEncoded타입의 Body를 Map으로 변환할 수 있다.")
    @Test
    void createUrlEncodedBody() {
        // given
        String input = "name=ehtjs&password=password";

        // when
        RequestBody requestBody = new RequestBody(input, ContentType.X_WWW_FORM_URLENCODED);

        // then
        assertThat(requestBody.getValue("name")).isEqualTo("ehtjs");
    }
}
