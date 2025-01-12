package org.apache.coyote.http11.request;

public class Path {

    private final String value;
    private final Extension extension;
    private final QueryParams queryParams;

    public Path(String value) {
        this.value = value;
        this.extension = initExtension(value);
        this.queryParams = initQueryParams(value);
    }

    private QueryParams initQueryParams(String path) {
        if (path.contains("?")) {
            String[] split = path.split("\\?");
            return new QueryParams(split[1]);
        }
        return null;
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

    public String getResourceContentType() {
        return extension.getContentType();
    }

    public String getQueryValue(String queryKey) {
        return  queryParams.getValue(queryKey);
    }
}
