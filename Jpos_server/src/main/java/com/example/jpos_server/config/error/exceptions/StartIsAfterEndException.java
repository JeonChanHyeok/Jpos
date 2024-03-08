package com.example.jpos_server.config.error.exceptions;

import com.example.jpos_server.config.error.ErrorCode;

public class StartIsAfterEndException extends RuntimeException{
    private ErrorCode errorCode;

    public StartIsAfterEndException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
