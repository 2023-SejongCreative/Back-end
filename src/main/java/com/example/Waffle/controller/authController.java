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


@Controller
public class authController {
;

    private final userService userService;

    public authController(userService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<String> login(@RequestParam("email") String email,
                        @RequestParam("password") String password){


        System.out.println(email);
        System.out.println(password);
        loginDto loginDto = new loginDto(email, password);

        userEntity userEntity = userService.login(loginDto);
        if(userEntity == null){
            return ResponseEntity.badRequest().body("실패");
        }

        return ResponseEntity.ok("성공");
    }
}
