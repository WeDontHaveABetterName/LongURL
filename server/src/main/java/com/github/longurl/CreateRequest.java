package com.github.longurl;

public class CreateRequest {
    public String algorithm;
    public String url;
    public int length;

    public CreateRequest(String algorithm, String url, int length) {
        this.algorithm = algorithm;
        this.url = url;
        this.length = length;
    }
}
