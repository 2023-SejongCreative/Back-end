package com.example.Waffle.controller;

import com.example.Waffle.dto.RoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RoomController {

    @PostMapping("/{group_id}/createroom")
    public ResponseEntity<String> createRoom(@PathVariable("group_id") Integer groupId,
                                             @RequestBody Map<String, String> param){

    RoomDto roomDto = new RoomDto(param.get("room_name"),
            Integer.parseInt(param.get("type")));

    String email = param.get("email");





        return ResponseEntity.ok("룸 생성에 성공하였습니다");
    }
}
