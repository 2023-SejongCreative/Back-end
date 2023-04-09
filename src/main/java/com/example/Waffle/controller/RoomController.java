package com.example.Waffle.controller;

import com.example.Waffle.dto.RoomDto;
import com.example.Waffle.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
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
    public ResponseEntity<Object> roomList(@PathVariable("group_id") int groupId,
                                           @RequestBody Map<String, String> param){

        String email = param.get("email");

        String roomList = roomService.roomList(email, groupId);

        return new ResponseEntity<>(roomList, HttpStatus.OK);
    }

    @PostMapping("/{room_id}/inviteroom")
    @ResponseBody
    public ResponseEntity<Object> inviteUser(@PathVariable("room_id") int roomId,
                                             @RequestBody Map<String, String> param){

        String email = param.get("email");

        roomService.inviteUser(roomId, email);

        return new ResponseEntity<>("룸에 " + email + "을 추가하였습니다.", HttpStatus.OK);
    }
}
