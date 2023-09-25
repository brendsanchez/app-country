package com.dusk.country.exception;

import org.springframework.http.HttpStatus;

public abstract class CountryException extends RuntimeException {
    protected final HttpStatus httpStatus;

    protected CountryException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus(){
        return this.httpStatus;
    }
}
