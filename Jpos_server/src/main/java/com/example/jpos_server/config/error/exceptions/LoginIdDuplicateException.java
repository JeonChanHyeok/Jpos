package com.example.jpos_server.config.error.exceptions;

import com.example.jpos_server.config.error.ErrorCode;

public class LoginIdDuplicateException extends RuntimeException{

    private ErrorCode errorCode;

    public LoginIdDuplicateException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
