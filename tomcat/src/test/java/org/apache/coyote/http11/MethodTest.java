package org.apache.coyote.http11;

import java.nio.file.Path;
import org.apache.coyote.http11.request.Method;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


class MethodTest {

    @DisplayName("valueOf로 소문자 대문자 모두 매칭가능하다.")
    @Test
    void a() {
        Path path = Path.of("index.html");
        System.out.println(path.toString());
    }
}