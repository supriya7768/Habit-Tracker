package com.bnt.response;

import org.springframework.http.HttpStatusCode;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ErrorResponse {
    private int statusCode;
    private String message;
    private  HttpStatusCode status;

    public ErrorResponse(int statusCode, String message) {  
        this.statusCode = statusCode;
        this.message = message;
    }

    public ErrorResponse(int statusCode, String message, HttpStatusCode status) {
        this.statusCode = statusCode;
        this.message = message;
        this.status = status;
    }

}
