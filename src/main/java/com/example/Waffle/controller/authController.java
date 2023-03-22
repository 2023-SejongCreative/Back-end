package com.example.Waffle.controller;


import com.example.Waffle.dto.userDto;
import com.example.Waffle.entity.userEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.Waffle.service.userService;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class authController {
    //생성자 주입
    private final userService userService;

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

        userDto userDto = new userDto(param.get("email"),
                param.get("password"),
                param.get("name"));

        userEntity userEntity = userDto.toEntity();
        userService.register(userEntity);
        return ResponseEntity.ok("회원가입 성공!");
    }


}
