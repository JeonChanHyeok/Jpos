package com.example.jpos_server.config.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    DUPLICATED_REQUEST(400, "DUPLICATED-REQUEST-400", "중복된 요청입니다."),
    NOT_MATCH_USER_AND_REQUEST(400, "NOT-MATCH-USER-AND-REQUEST-400", "잘못된 요청입니다."),
    BAD_LOGIN(400, "BAD-CREDENTIALS-400","로그인 실패"),
    LOGIN_ID_DUPLICATION(400,"SIGN-UP-400", "아이디 중복"),
    SEAT_NOT_EXIST_IN_STORE(462, "SEAT-ERROR-400", "잘못된 접근입니다."),
    START_IS_AFTER_END(400, "START-IS-AFTER-END-400", "검색 시작 시간과 끝 시간을 확인해 주십시오.")
    ;

    private final int status;
    private final String errorCode;
    private final String message;
}
