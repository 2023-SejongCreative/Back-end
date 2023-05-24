package com.example.Waffle.entity;


import com.example.Waffle.entity.Group.GroupEntity;
import com.example.Waffle.entity.Room.RoomEntity;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }

    public void changeDate(LocalDate date){
        this.date = date;
    }

    public void changeNotice(int notice){
        this.notice = notice;
    }


    @Builder
    public NoteEntity(String title, String content, LocalDate date, int notice, GroupEntity group, RoomEntity room, UserEntity user){
        this.title = title;
        this.content = content;
        this.date = date;
        this.notice = notice;
        this.group = group;
        this.room = room;
        this.user = user;
    }
}
