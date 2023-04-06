package com.example.Waffle.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RoomController {

    @PostMapping("/{group_id}/createroom")
    public ResponseEntity<String> createRoom(@PathVariable("group_id") Integer groupId){



        return ResponseEntity.ok("룸 생성에 성공하였습니다");
    }
}
