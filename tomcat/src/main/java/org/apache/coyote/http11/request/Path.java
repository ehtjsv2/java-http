package org.apache.coyote.http11.request;

public class Path {

    private final String value;
    private final Extension extension;

    public Path(String value) {
        this.value = value;
        this.extension = initExtension(value);
    }

    private Extension initExtension(String path) {
        String[] splits = path.split("\\.");
        if (splits.length == 2) {
            return Extension.valueOf(splits[1].toUpperCase());
        }
        return null;
    }

    public Extension getExtension() {
        if (extension == null) {
            throw new IllegalArgumentException("확장자가 존재하지 않는 PATH입니다.");
        }
        return extension;
    }

    public boolean isExistExtension() {
        return extension != null;
    }

    public String getValue() {
        return value;
    }

    public boolean isDefaultPath() {
        return value.equals("/");
    }
}
