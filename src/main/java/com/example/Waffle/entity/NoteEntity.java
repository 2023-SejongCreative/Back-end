package com.example.Waffle.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "note")
public class NoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private LocalDate date;
    private int notice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private GroupEntity group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private RoomEntity room;

    @Builder
    public NoteEntity(String title, String content, LocalDate date, int notice, GroupEntity group, RoomEntity room){
        this.title = title;
        this.content = content;
        this.date = date;
        this.notice = notice;
        this.group = group;
        this.room = room;
    }
}
