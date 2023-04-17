package com.example.Waffle.controller;

import com.example.Waffle.dto.GroupDto;
import com.example.Waffle.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping("/creategroup")
    public ResponseEntity<Object> createGroup(@RequestBody Map<String, String> param){
        GroupDto groupDto = new GroupDto(param.get("group_name"));

        Long id = groupService.createGroup(param.get("email"),groupDto);


        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping("/{user_email}/groups")
    public @ResponseBody ResponseEntity<Object> groupList(@PathVariable("user_email") String email){

        String groupList = groupService.userGroupList(email);

        return new ResponseEntity<>(groupList, HttpStatus.OK);
    }

    @PostMapping("/{group_id}/invitegroup")
    public ResponseEntity<Object> inviteUser(@PathVariable("group_id") int groupId,
                                             @RequestBody Map<String, String> param){

        System.out.println(groupId);
        String email = param.get("email");
        groupService.inviteUser(groupId, email);

        return new ResponseEntity<>("그룹에" + email + "을 추가하였습니다.", HttpStatus.OK);
    }

    @DeleteMapping("/{group_id}/deletegroup")
    public ResponseEntity<Object> deleteGroup(@PathVariable("group_id") int groupId){

        //반복문으로 룸 지우기

        groupService.deleteGroup(groupId);

        return new ResponseEntity<>("그룹 삭제에 성공하였습니다.", HttpStatus.OK);
    }

}
