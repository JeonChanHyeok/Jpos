package com.example.jpos_server.config.error.exceptions;

import com.example.jpos_server.config.error.ErrorCode;

public class SeatNotExistInStoreException extends RuntimeException{

    private ErrorCode errorCode;

    public SeatNotExistInStoreException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
