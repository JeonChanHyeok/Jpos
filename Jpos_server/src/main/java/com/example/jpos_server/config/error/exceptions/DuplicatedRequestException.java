package com.example.jpos_server.config.error.exceptions;

import com.example.jpos_server.config.error.ErrorCode;

public class DuplicatedRequestException extends RuntimeException {
    private ErrorCode errorCode;

    public DuplicatedRequestException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
