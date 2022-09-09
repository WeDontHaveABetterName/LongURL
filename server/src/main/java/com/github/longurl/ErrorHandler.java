package com.github.longurl;

import com.github.longurl.exceptions.InvalidURLException;
import com.github.longurl.exceptions.NoEntryFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.NoSuchAlgorithmException;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({ NoSuchAlgorithmException.class, InvalidURLException.class, NoEntryFoundException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNoSuchAlgorithmException(RuntimeException exception) {
        return new ErrorResponse(exception.getMessage());
    }

}
