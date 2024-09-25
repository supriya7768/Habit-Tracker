package com.bnt.exception;

import org.springframework.http.HttpStatusCode;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserException extends RuntimeException {

    private final String exceptionMessage;
    private final Integer statusCode;
    private final HttpStatusCode status;
    
    public UserException(String message, Integer statusCode, HttpStatusCode status){
        super(message);
        this.exceptionMessage = message;
        this.statusCode = statusCode;
        this.status = status;
    }
    
}
