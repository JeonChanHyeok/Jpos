package com.example.jpos_server.config.error;

import com.example.jpos_server.config.error.exceptions.*;
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

    //중복된 요청
    @ExceptionHandler(DuplicatedRequestException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatedRequestException(){
        ErrorResponse response = new ErrorResponse(ErrorCode.DUPLICATED_REQUEST);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    //요청과 요청자 매칭 안됨.
    @ExceptionHandler(NotMatchingUserAndRequestException.class)
    public ResponseEntity<ErrorResponse> handleNotMatchingUserAndRequestException(){
        ErrorResponse response = new ErrorResponse(ErrorCode.NOT_MATCH_USER_AND_REQUEST);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    //주문 검색 시작시간과 끝시간 오류
    @ExceptionHandler(StartIsAfterEndException.class)
    public ResponseEntity<ErrorResponse> handleStartIsAfterEndException(){
        ErrorResponse response = new ErrorResponse(ErrorCode.START_IS_AFTER_END);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
