package org.apache.coyote.http11.request;

public class Path {

    private final String value;
    private final ContentType contentType;
    private final QueryParams queryParams;

    public Path(String value) {
        this.value = value;
        this.contentType = initExtension(value);
        this.queryParams = initQueryParams(value);
    }

    private QueryParams initQueryParams(String path) {
        if (path.contains("?")) {
            String[] split = path.split("\\?");
            return new QueryParams(split[1]);
        }
        return null;
    }

    private ContentType initExtension(String path) {
        String[] splits = path.split("\\.");
        if (splits.length == 2) {
            return ContentType.valueOf(splits[1].toUpperCase());
        }
        return null;
    }

    public ContentType getExtension() {
        if (contentType == null) {
            throw new IllegalArgumentException("확장자가 존재하지 않는 PATH입니다.");
        }
        return contentType;
    }

    public boolean isExistExtension() {
        return contentType != null;
    }

    public String getValue() {
        return value;
    }

    public boolean isDefaultPath() {
        return value.equals("/");
    }

    public ContentType getResourceContentType() {
        return contentType;
    }

    public String getQueryValue(String queryKey) {
        return  queryParams.getValue(queryKey);
    }
}
