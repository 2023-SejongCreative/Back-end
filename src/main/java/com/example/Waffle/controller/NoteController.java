package com.example.Waffle.controller;


import com.example.Waffle.dto.NoteDto;
import com.example.Waffle.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
    public ResponseEntity<Object> createNote(@PathVariable("type") String type,
                                             @PathVariable("type_id") Long id,
                                             @RequestBody Map<String, String> param,
                                             @RequestHeader("access_token") String atk){


        LocalDate date = LocalDate.parse(param.get("date"), DateTimeFormatter.ISO_DATE);

        NoteDto noteDto = new NoteDto(param.get("title"),
                param.get("content"), date, Integer.parseInt(param.get("notice")));


        Long noteId = noteService.createNote(noteDto, type, id, atk);

        return new ResponseEntity<>(noteId, HttpStatus.OK);
    }

    @GetMapping("/note/{type}/{type_id}/notelist")
    @ResponseBody
    public ResponseEntity<Object> getNotes(@PathVariable("type") String type,
                         @PathVariable("type_id") int intId){

        Long id = Long.valueOf(intId);

        System.out.println(type);
        System.out.println(id);
        String noteList = noteService.getNotes(type, id);
        System.out.println(noteList);

        return new ResponseEntity<>(noteList, HttpStatus.OK);
    }

    @GetMapping("/note/{note_id}")
    @ResponseBody
    public ResponseEntity<Object> getNote(@PathVariable("note_id") int id){

        String note = noteService.getNote(id);

        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @PostMapping("/note/{note_id}/update/{manager}")
    @ResponseBody
    public ResponseEntity<String> updateNote(@PathVariable("note_id") int id,
                                             @PathVariable("manager") int manager,
                                             @RequestBody Map<String, String> param,
                                             @RequestHeader("access_token") String atk){

        System.out.println(manager);

        LocalDate date = LocalDate.parse(param.get("date"), DateTimeFormatter.ISO_DATE);

        NoteDto noteDto = new NoteDto(param.get("title"), param.get("content"), date, Integer.parseInt(param.get("notice")));
        System.out.println(param.get("notice"));

        noteService.updateNote(noteDto, id, atk, manager);

        return ResponseEntity.ok("게시글 수정에 성공하였습니다.");
    }

    @DeleteMapping("/note/{note_id}/delete/{manager}")
    @ResponseBody
    public ResponseEntity<String> deleteNote(@PathVariable("note_id") int id,
                                             @PathVariable("manager") int manager,
                                             @RequestHeader("access_token") String atk){

        System.out.println(manager);

        noteService.deleteNote(id, atk, manager);

        return ResponseEntity.ok("게시글 삭제가 완료되었습니다.");
    }
}
