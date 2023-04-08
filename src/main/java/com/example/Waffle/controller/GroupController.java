package com.example.Waffle.controller;

import com.example.Waffle.dto.GroupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class GroupController {

    @PostMapping("/creategroup")
    public ResponseEntity<Object> createGroup(@RequestBody Map<String, String> param){
        GroupDto groupDto = new GroupDto(param.get("group_name"));


        return new ResponseEntity<>("그룹 생성에 성공하였습니다.", HttpStatus.OK);
    }

}
