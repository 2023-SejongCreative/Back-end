package com.example.Waffle.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserException extends RuntimeException{

    private ErrorCode errorCode;
}
