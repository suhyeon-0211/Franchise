package com.franchise.Franchise.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BusinessLogicException extends ServerException {
    public BusinessLogicException(BusinessLogicExceptionReason reason) {
        super(reason.getErrorCode(), reason.getErrorMsg(), reason.getTranslate(), null, reason.getStatus());
    }

    public BusinessLogicException(BusinessLogicExceptionReason reason, Throwable cause) {
        this(reason.getErrorCode(), reason.getErrorMsg(), reason.getTranslate(), cause, reason.getStatus());
    }

    protected BusinessLogicException(int errorCode, String errorMsg, String translate) {
        this(errorCode, errorMsg, translate, null, HttpStatus.BAD_REQUEST);
    }

    protected BusinessLogicException(int errorCode, String errorMsg, String translate, Throwable cause, HttpStatus status) {
        super(errorCode, errorMsg, translate, cause, status);
    }
}
