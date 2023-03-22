package com.example.Waffle.controller;

import com.example.Waffle.dto.loginDto;
import com.example.Waffle.entity.userEntity;
import com.example.Waffle.exception.errorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.Waffle.service.userService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Controller
public class authController {
;

    private final userService userService;

    public authController(userService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<String> login(@RequestBody Map<String, String> param){

        String email = param.get("email");
        String password = param.get("password");

        System.out.println(email);
        System.out.println(password);
        loginDto loginDto = new loginDto(email, password);

        userEntity userEntity = userService.login(loginDto);
        if(userEntity == null){
            return ResponseEntity.badRequest().body("실패");
        }
        return ResponseEntity.ok("로그인에 성공하셨습니다.");
    }
}
