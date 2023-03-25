package com.example.Waffle.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTER_SERVER_ERROR(500,"서버 내부 오류 발생"),
    NO_USER(400, "일치하는 회원이 없습니다."),
    NO_PASSWORD(400, "비밀번호가 일치하지 않습니다.");

    private int status;
    private String message;
}
