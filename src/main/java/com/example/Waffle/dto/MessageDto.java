package com.example.Waffle.dto;

import com.example.Waffle.entity.DM.DmEntity;
import com.example.Waffle.entity.DM.MessageEntity;
import com.example.Waffle.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private String chat;
    private LocalDateTime time;
    private UserEntity user;
    private DmEntity dm;



    public MessageEntity toEntity(){
        MessageEntity messageEntity = new MessageEntity().builder()
                .chat(chat)
                .time(time)
                .user(user)
                .dm(dm)
                .build();
        return messageEntity;
    }

}
