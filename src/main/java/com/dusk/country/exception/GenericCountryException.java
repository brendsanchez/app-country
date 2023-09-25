package com.dusk.country.exception;

import org.springframework.http.HttpStatus;

public class GenericCountryException extends CountryException{

    public GenericCountryException(String message){
        super(message, HttpStatus.NOT_FOUND);
    }

    public GenericCountryException(String message, HttpStatus status){
        super(message, status);
    }
}
