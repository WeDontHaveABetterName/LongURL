package com.github.longurl.exceptions;

public class RequestedURLLengthInvalidException extends RuntimeException {

    public RequestedURLLengthInvalidException(String message) {
        super(message);
    }
}
