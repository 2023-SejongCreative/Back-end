package com.example.Waffle.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class DmController {

    @PostMapping("/chat/create")
    public ResponseEntity<Object> creatChat (@RequestBody Map<String, String> param){
        return new ResponseEntity<>("만들어졌어요", HttpStatus.OK);
    }




}
