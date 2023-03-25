package com.example.Waffle.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTER_SERVER_ERROR(500,"서버 내부 오류 발생"),
    NO_USER(400, "일치하는 회원이 없습니다."),
    NO_PASSWORD(400, "비밀번호가 일치하지 않습니다.");

    private int status;

    /*400 BAD_REQUEST : 잘못된 요청*/
    NO_USER(HttpStatus.BAD_REQUEST, "LOGIN-001", "일치하는 회원이 없습니다."),
    NO_PASSWORD(HttpStatus.BAD_REQUEST, "LOGIN-002", "비밀번호가 일치하지 않습니다."),

    /*409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재*/
    DUPLICATE_ID(HttpStatus.CONFLICT, "REGISTER-001", "아이디가 중복되었습니다."),

    /*500 INTERNAL_SERVER_ERROR : 서버 오류*/
    INTER_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-001", "서버 내부 오류 발생"),
    ;

    private HttpStatus httpStatus;
    private String code;
    private String message;
}
