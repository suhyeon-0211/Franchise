package com.franchise.Franchise.exceptions;

import org.springframework.http.HttpStatus;

public interface BusinessLogicExceptionReason {

    int getErrorCode();

    String getErrorMsg();

    default String getTranslate() {
        return getErrorMsg();
    }

    HttpStatus getStatus();
}
