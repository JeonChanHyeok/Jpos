package com.example.jpos_server.config.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    BAD_REQUEST(400, "BAD-REQUEST", "잘못된 요청"),
    BAD_LOGIN(460, "BAD-CREDENTIALS-460","로그인 실패"),
    LOGIN_ID_DUPLICATION(461,"SIGN-UP-461", "아이디 중복"),
    SEAT_NOT_EXIST_IN_STORE(462, "SEAT-ERROR-462", "잘못된 접근입니다."),
    ;

    private final int status;
    private final String errorCode;
    private final String message;
}
