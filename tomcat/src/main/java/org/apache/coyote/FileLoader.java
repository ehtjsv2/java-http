package org.apache.coyote;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

public class FileLoader {

    public static String readResourceAllLine(String resourcePath) {
        URL resource = ClassLoader.getSystemClassLoader().getResource(resourcePath);
        try {
            return new String(Files.readAllBytes(new File(resource.getFile()).toPath()));
        } catch (IOException e) {
            throw new IllegalArgumentException("파일을 불러오는 과정에서 에러가 발생했습니다.: " + resourcePath);
        }
    }
}
