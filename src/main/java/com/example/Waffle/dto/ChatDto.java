package com.example.Waffle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {
    private int dmId;
    private String content;
    private String sender;
    private LocalDateTime time;
}
