package com.example.Waffle.dto;


import com.example.Waffle.entity.GroupEntity;
import com.example.Waffle.entity.NoteEntity;
import com.example.Waffle.entity.RoomEntity;
import com.example.Waffle.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteDto {

    private String title;
    private String content;
    private LocalDate date;
    private int notice;

    private GroupEntity group;
    private RoomEntity room;
    private UserEntity user;

    public NoteDto(String title, String content, LocalDate date, int notice){
        this.title = title;
        this.content = content;
        this.date = date;
        this.notice = notice;
    }

    public NoteEntity toEntity(){
        NoteEntity noteEntity = new NoteEntity().builder()
                .title(title)
                .content(content)
                .date(date)
                .notice(notice)
                .group(group)
                .room(room)
                .user(user)
                .build();

        return noteEntity;
    }

}
