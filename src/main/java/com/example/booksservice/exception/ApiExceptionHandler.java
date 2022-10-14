package com.example.booksservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiRequestException.class)
    public ResponseEntity<?> handleApiRequestException(ApiRequestException apiRequestException){
          ApiException apiException =  new ApiException(apiRequestException.getMessage()
                  , HttpStatus.BAD_REQUEST);
          return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }
}
