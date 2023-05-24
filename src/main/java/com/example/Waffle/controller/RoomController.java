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
    public ResponseEntity<Object> createRoom(@PathVariable("group_id") int groupId,
                                             @RequestHeader("access_token") String accessToken,
                                              @RequestBody Map<String, String> param){

        RoomDto roomDto = new RoomDto(param.get("room_name"),
            Integer.parseInt(param.get("type")));

        Long id = roomService.createRoom(roomDto, accessToken, groupId);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping("/{group_id}/rooms")
    @ResponseBody
    public ResponseEntity<Object> roomList(@PathVariable("group_id") int groupId,
                                           @RequestHeader("access_token") String accessToken){



        String roomList = roomService.roomList(accessToken, groupId);

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

    @DeleteMapping("/{room_id}/deleteroom")
    @ResponseBody
    public ResponseEntity<Object> deleteRoom(@PathVariable("room_id") Long roomId){

        roomService.deleteRoom(roomId);

        return new ResponseEntity<>("룸 삭제에 성공하였습니다.", HttpStatus.OK);
    }
}
