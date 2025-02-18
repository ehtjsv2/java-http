package org.apache.coyote;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileLoaderTest {

    @DisplayName("파일을 문자열로 불러올 수 있다.")
    @Test
    void readAllLine() {
        String expected = "대시보드";

        String loginHtml = FileLoader.readResourceAllLine("index.html");

        assertThat(loginHtml).contains(expected);
    }
}
