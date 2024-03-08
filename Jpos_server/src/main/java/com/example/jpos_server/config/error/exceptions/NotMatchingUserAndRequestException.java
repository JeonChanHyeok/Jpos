package com.example.jpos_server.config.error.exceptions;

import com.example.jpos_server.config.error.ErrorCode;

public class NotMatchingUserAndRequestException extends RuntimeException{
    private ErrorCode errorCode;

    public NotMatchingUserAndRequestException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }

}
