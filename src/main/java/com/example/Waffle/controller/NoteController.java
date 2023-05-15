package com.example.Waffle.controller;


import com.example.Waffle.dto.NoteDto;
import com.example.Waffle.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping("/note/{type}/{type_id}/create")
    @ResponseBody
    public ResponseEntity<String> createNote(@PathVariable("type") String type,
                                             @PathVariable("type_id") int id,
                                             @RequestBody Map<String, String> param){

        LocalDate date = LocalDate.parse(param.get("date"), DateTimeFormatter.ISO_DATE);

        NoteDto noteDto = new NoteDto(param.get("title"),
                param.get("content"), date, Integer.parseInt(param.get("notice")));


        noteService.createNote(noteDto, type, id);

        return ResponseEntity.ok("게시글 생성이 완료되었습니다.");
    }

    @GetMapping("/note/{type}/{type_id}/notelist")
    @ResponseBody
    public void getNotes(@PathVariable("type") String type,
                                           @PathVariable("type_id") int id){


    }

    @GetMapping("/note/{note_id}")
    @ResponseBody
    public void getNote(@PathVariable("note_id") int id){

    }

    @PostMapping("/note/{note_id}/update")
    @ResponseBody
    public ResponseEntity<String> updateNote(@PathVariable("note_id") int id){

        return ResponseEntity.ok("게시글 수정에 성공하였습니다.");
    }

    @DeleteMapping("/note/{note_id}/delete")
    @ResponseBody
    public ResponseEntity<String> deleteNote(@PathVariable("note_id") int id){

        return ResponseEntity.ok("게시글 삭제가 완료되었습니다.");
    }
}
