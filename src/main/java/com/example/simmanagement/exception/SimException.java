package com.example.simmanagement.exception;

import org.springframework.http.HttpStatus;

public class SimException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public SimException(String message) {
        super(message);
    }

    public SimException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
