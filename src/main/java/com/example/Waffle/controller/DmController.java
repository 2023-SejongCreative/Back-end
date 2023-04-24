package com.example.Waffle.controller;

import com.example.Waffle.dto.DmDto;
import com.example.Waffle.service.DmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class DmController {

    private DmService dmService;

    @PostMapping("/chat/create")
    public ResponseEntity<Object> creatChat (@RequestBody Map<String, Object> param){
        List<Object> email = Arrays.asList(param.get("email"));
        //email의 갯수
        Long cnt = email.stream()
                .filter(e -> e!= null)
                .count();

        int count = cnt.intValue();

        //List<Object>를 String 으로 변경
        List<String> emails = email.stream()
                .map(object -> Objects.toString(object, null))
                .toList();

        DmDto dmDto = new DmDto(param.get("name").toString(), count);

        Long id = dmService.createDm(dmDto, emails);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }






}
