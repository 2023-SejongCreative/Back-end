package com.example.Waffle.controller;

import com.example.Waffle.dto.ChatDto;
import com.example.Waffle.dto.DmDto;
import com.example.Waffle.exception.ErrorCode;
import com.example.Waffle.exception.UserException;
import com.example.Waffle.service.DmService;
import com.example.Waffle.service.MessageService;
import com.example.Waffle.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class DmController {

    private final DmService dmService;
    private final JwtTokenProvider jwtTokenProvider;
    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final MessageService messageService;


    @PostMapping("/chat/create")
    public ResponseEntity<Object> creatChat (@RequestBody Map<String, List<String>> param,
                                             @RequestHeader("access_token") String accessToken){
        List<String> emails;
        emails = param.get("email");

        emails.add(jwtTokenProvider.getEmail(accessToken));

        int count = emails.size();

        String chat_name = param.get("chat_name").get(0);

        DmDto dmDto = new DmDto(chat_name, count);
        dmDto.setLast_chat("채팅방이 생성되었습니다.");
        dmDto.setLast_time(LocalDateTime.now());

        Long id = dmService.createDm(dmDto, emails);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }


    @PostMapping("/chat/{chat_id}/invite")
    public ResponseEntity<Object> inviteUser (@RequestBody Map<String, String> param,
                                              @PathVariable("chat_id") int dmId){

        String email = param.get("email");
        dmService.inviteDm(dmId, email);

        return new ResponseEntity<>("채팅방에 " + email + " 을 초대하였습니다.", HttpStatus.OK);
    }


    @GetMapping("/chat/{chat_id}/userlist")
    @ResponseBody
    public ResponseEntity<Object> userList (@PathVariable("chat_id") int dmId){

        String userList = dmService.dmUserList(dmId);

        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/chat/chatlist")
    @ResponseBody
    public ResponseEntity<Object> chatList(@RequestHeader("access_token") String accessToken){

        String email = jwtTokenProvider.getEmail(accessToken);

        String dmList = dmService.dmList(email);

        return new ResponseEntity<>(dmList, HttpStatus.OK);
    }

    @PostMapping("/chat/{chat_id}/delete")
    public ResponseEntity<Object> leaveChat(@RequestHeader("access_token") String accessToken,
                                            @PathVariable("chat_id") int dmId) {

        String email = jwtTokenProvider.getEmail(accessToken);
        dmService.leaveChat(email,dmId);

        return new ResponseEntity<>("채팅방 나가기에 성공하셨습니다.", HttpStatus.OK);
    }

    @GetMapping("/chat/{chat_id}/messagelist")
    @ResponseBody
    public ResponseEntity<Object> messageList(@PathVariable("chat_id") int dmId){
        String messageList = messageService.messageList(dmId);
        return new ResponseEntity<>(messageList, HttpStatus.OK);
    }

    @MessageMapping("/chat")
    public void message(ChatDto chatDto, @Header("access_token") String accessToken){

        String email = jwtTokenProvider.getEmail(accessToken);
        System.out.println("[" + chatDto.getUser_email() + "] : " + chatDto.getContent());

        ChatDto message = messageService.createChatDto(chatDto);

        messageService.saveMessage(message);

        simpMessageSendingOperations.convertAndSend("/sub/chat/" + message.getDmId(), message);

    }


}
