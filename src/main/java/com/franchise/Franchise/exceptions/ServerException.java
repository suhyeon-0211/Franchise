package com.franchise.Franchise.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ServerException extends RuntimeException {
    private int errorCode;
    private String translate;
    private HttpStatus status;

    public ServerException(int errorCode, final String errorMsg, String translate, HttpStatus status) {
        this(errorCode, errorMsg, translate, null, status);
    }

    public ServerException(int errorCode, final String errorMsg, String translate, Throwable cause, HttpStatus status) {
        super(errorMsg, cause);

        this.errorCode = errorCode;
        this.translate = translate;
        this.status = status;
    }
}