package com.agubin.cards.exceptions;

import org.springframework.http.HttpStatus;


public class ExceptionData {

    private HttpStatus status;
    private String message;

    public ExceptionData(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
    }


    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
