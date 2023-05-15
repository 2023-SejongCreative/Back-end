package com.example.Waffle.dto;


import com.example.Waffle.entity.GroupEntity;
import com.example.Waffle.entity.NoteEntity;
import com.example.Waffle.entity.RoomEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoteDto {

    private String title;
    private String content;
    private LocalDate date;
    private int notice;

    private GroupEntity group;
    private RoomEntity room;

    public NoteEntity toEntity(){
        NoteEntity noteEntity = new NoteEntity().builder()
                .title(title)
                .content(content)
                .date(date)
                .notice(notice)
                .group(group)
                .room(room)
                .build();

        return noteEntity;
    }

}
