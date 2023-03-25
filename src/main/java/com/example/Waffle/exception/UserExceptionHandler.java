package com.example.Waffle.exception;

import com.example.Waffle.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(value={UserException.class})
    public ResponseEntity<Object> handleUserExcption(UserException e){
        ResponseDto responseDto  = new ResponseDto(e.getErrorCode().getHttpStatus().value(), e.getErrorCode().getCode(), e.getErrorCode().getMessage());
        return new ResponseEntity<>(responseDto, e.getErrorCode().getHttpStatus());
    }
}
