package com.github.longurl.api;

public class CreateRequest {
    public String algorithm;
    public String url;
    public Integer length;

    public CreateRequest(String algorithm, String url, Integer length) {
        this.algorithm = algorithm;
        this.url = url;
        this.length = length;
    }

    public boolean hasNulls() {
        return algorithm == null || url == null || length == null;
    }
}
