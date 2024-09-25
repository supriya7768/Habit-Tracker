package com.bnt.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bnt.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

     private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public ResponseEntity<ErrorResponse> handleUserException(UserException ex){
        logger.error("UserException in {} : {}", ex.getExceptionMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse(ex.getStatusCode(),ex.getMessage(), ex.getStatus());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    public ResponseEntity<ErrorResponse> handleTargetException(TargetException ex){
        logger.error("TargetException in {} : {}", ex.getExceptionMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse(ex.getStatusCode(),ex.getMessage(), ex.getStatus());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    public ResponseEntity<ErrorResponse> handleHabitException(HabitException ex){
        logger.error("HabitException in {} : {}", ex.getExceptionMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse(ex.getStatusCode(),ex.getMessage(), ex.getStatus());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    
}
