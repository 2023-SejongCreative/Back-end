package com.example.Waffle.controller;

import com.example.Waffle.dto.UserDto;
import com.example.Waffle.dto.LoginDto;
import com.example.Waffle.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.Waffle.service.UserService;

import java.util.Map;


@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

//    @GetMapping("/")
//    public String index(){
//        return "index.html";
//    }
//
//    @GetMapping("/register")
//    public String register(){
//        return "register.html";
//    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<String> register(@RequestBody Map<String, String> param){

        UserDto userDto = new UserDto(param.get("email"),
                param.get("password"),
                param.get("name"));

//        UserEntity userEntity = userDto.toEntity();
//        userService.register(userEntity);

        userService.save(userDto);

        return ResponseEntity.ok("회원가입에 성공하였습니다.");
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<String> login(@RequestBody Map<String, String> param){

        LoginDto loginDto = new LoginDto(param.get("email"),
                param.get("password"));

        userService.login(loginDto);

        return ResponseEntity.ok("로그인에 성공하셨습니다.");
    }
}
