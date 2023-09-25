package com.dusk.country.exception;

import com.dusk.country.dto.response.CountryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.HttpURLConnection;

import static java.util.Objects.nonNull;

@ControllerAdvice
@SuppressWarnings("unused")
public class CountryExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(CountryExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CountryResponse<Void>> methodArgumentNotValidException(Errors errors) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        var fieldErrors = errors.getFieldError();
        var message = nonNull(fieldErrors) ? fieldErrors.getDefaultMessage() : null;
        logger.error(message);

        var countryResponse = new CountryResponse<Void>(status.value(),
                message,
                null);
        return ResponseEntity.status(status).body(countryResponse);
    }

    @ExceptionHandler(CountryException.class)
    public ResponseEntity<CountryResponse<Void>> countryException(CountryException countryException) {
        logger.error(countryException.getMessage());
        var countryResponse = new CountryResponse<Void>(countryException.getHttpStatus().value(),
                countryException.getHttpStatus().getReasonPhrase(),
                null);
        return ResponseEntity.status(countryException.httpStatus).body(countryResponse);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CountryResponse<Void> createAccountsException(Exception ex) {
        logger.error(ex.getMessage());
        return new CountryResponse<>(HttpURLConnection.HTTP_INTERNAL_ERROR,
                "error intern",
                null);
    }
}
