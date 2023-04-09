package com.example.Waffle.controller;

import com.example.Waffle.dto.RoomDto;
import com.example.Waffle.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/{group_id}/createroom")
    public ResponseEntity<Object> createRoom(@PathVariable("group_id") Integer groupId,
                                             @RequestBody Map<String, String> param){

        RoomDto roomDto = new RoomDto(param.get("room_name"),
            Integer.parseInt(param.get("type")));

        String email = param.get("email");

        roomService.createRoom(roomDto, email, groupId);

        return new ResponseEntity<>("룸 생성에 성공하였습니다", HttpStatus.OK);
    }

    @GetMapping("/{group_id}/rooms")
    @ResponseBody
    public ResponseEntity<Object> roomList(@PathVariable("group_id") Integer groupId,
                                           @RequestBody Map<String, String> param){

        String email = param.get("email");



        return new ResponseEntity<>("룸 목록을 불러오는데 성공했습니다.", HttpStatus.OK);
    }
}
