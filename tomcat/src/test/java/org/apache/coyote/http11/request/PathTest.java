package org.apache.coyote.http11.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PathTest {

    @DisplayName("확장자가 없는 PATH면 예외가 발생한다.")
    @Test
    void exception_when_no_extension() {
        Path path = new Path("/index");

        assertThatThrownBy(path::getExtension)
                .hasMessage("확장자가 존재하지 않는 PATH입니다.");
    }

    @DisplayName("확장자를 얻을 수 있다.")
    @ParameterizedTest
    @ValueSource(strings = {"/index.html", "/index.css", "/index.js"})
    void getExtension(String path) {
        Path path1 = new Path(path);

        assertDoesNotThrow(path1::getExtension);
    }

    @DisplayName("정적리소스를 요청인지 구분할 수 있다.(true)")
    @Test
    void isExistExtension_true() {
        Path path = new Path("/index.html");

        assertThat(path.isExistExtension()).isTrue();
    }

    @DisplayName("정적리소스를 요청인지 구분할 수 있다.(false)")
    @Test
    void isExistExtension_false() {
        Path path = new Path("/index");

        assertThat(path.isExistExtension()).isFalse();
    }

    @DisplayName("요청경로가 없으면 기본경로로 판단한다.(true)")
    @Test
    void isDefaultPath() {
        Path path = new Path("/");

        assertThat(path.isDefaultPath()).isTrue();
    }
}