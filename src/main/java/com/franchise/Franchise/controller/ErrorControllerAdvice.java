package com.franchise.Franchise.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.franchise.Franchise.exceptions.BusinessLogicException;
import com.franchise.Franchise.response.EmptyResponse;
import com.franchise.Franchise.response.GenericResponse;
import com.franchise.Franchise.response.ResponseMeta;

@ControllerAdvice
public class ErrorControllerAdvice {
    @ExceptionHandler(BusinessLogicException .class)
    public ResponseEntity<EmptyResponse> businessLogicException(final BusinessLogicException ex) {
        final ResponseMeta meta = new ResponseMeta(ex.getErrorCode(), ex.getMessage(), ex.getTranslate());

        HttpStatus status = ex.getStatus();

        return GenericResponse.of(status, meta);
    }
}