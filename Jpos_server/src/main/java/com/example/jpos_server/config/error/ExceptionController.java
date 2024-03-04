package com.example.jpos_server.config.error;

import com.example.jpos_server.config.error.exceptions.LoginIdDuplicateException;
import com.example.jpos_server.config.error.exceptions.SeatNotExistInStoreException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    //로그인 실패
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(){
        ErrorResponse response = new ErrorResponse(ErrorCode.BAD_LOGIN);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    //아이디 중복
    @ExceptionHandler(LoginIdDuplicateException.class)
    public ResponseEntity<ErrorResponse> handleLoginIdDuplicateException(){
        ErrorResponse response = new ErrorResponse(ErrorCode.LOGIN_ID_DUPLICATION);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    //QrOrder 페이지 들어갈 때 좌석과 자리 번호가 맞지 않을 때 발생.
    @ExceptionHandler(SeatNotExistInStoreException.class)
    public ResponseEntity<ErrorResponse> handleSeatNotExistInStoreException(){
        ErrorResponse response = new ErrorResponse(ErrorCode.SEAT_NOT_EXIST_IN_STORE);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
