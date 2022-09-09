package com.github.longurl;

public class CreateRequest {
    public String algorithm;
    public String url;
    public int depth;

    public CreateRequest(String algorithm, String url, int depth) {
        this.algorithm = algorithm;
        this.url = url;
        this.depth = depth;
    }
}
