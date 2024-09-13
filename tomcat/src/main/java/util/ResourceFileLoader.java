package util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

public class ResourceFileLoader {

    public static String loadStaticFileToString(String filePath) {
        URL resource = ResourceFileLoader.class.getClassLoader().getResource("static" + filePath);
        if (resource == null) {
            throw new IllegalArgumentException("존재하지 않는 staticFile을 요청했습니다: " + filePath);
        }
        try {
            return new String(Files.readAllBytes(new File(resource.getFile()).toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
