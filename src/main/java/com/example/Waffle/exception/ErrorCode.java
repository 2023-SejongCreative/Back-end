package com.example.Waffle.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTER_SERVER_ERROR(500, "COMMON-001", "서버 내부 오류 발생"),

    DUPLICATE_ID(409, "REGISTER-001", "아이디가 중복되었습니다."),

    NO_USER(400, "LOGIN-001", "일치하는 회원이 없습니다."),
    NO_PASSWORD(400, "LOGIN-002", "비밀번호가 일치하지 않습니다.");

    private int status;
    private String code;
    private String message;
}
