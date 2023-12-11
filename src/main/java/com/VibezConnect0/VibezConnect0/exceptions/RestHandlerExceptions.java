package com.VibezConnect0.VibezConnect0.exceptions;

import com.VibezConnect0.VibezConnect0.dto.ErrorBody;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class RestHandlerExceptions extends RuntimeException{

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorBody> handleResourceNotFoundException(
            ResourceNotFoundException rnfe, HttpServletRequest request) {

        ErrorBody errorDetail = new ErrorBody();
        errorDetail.setMessage(rnfe.getMessage()); // Set the exception message
        errorDetail.setStatusCode(HttpStatus.NOT_FOUND.value()); // Set the HTTP status code

        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }
}