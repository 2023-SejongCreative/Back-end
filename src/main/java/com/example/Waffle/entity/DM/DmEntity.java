package com.example.Waffle.entity.DM;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "dm")
public class DmEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String last_chat;
    private LocalDateTime last_time;
    private int count;

    @OneToMany(mappedBy = "dm")
    private List<MessageEntity> message = new ArrayList<>();

    @OneToOne(mappedBy = "dmEntity")
    private VideoEntity videoEntity;

    @Builder
    public DmEntity(String name, int count, String last_chat, LocalDateTime last_time){
        this.name = name;
        this.count = count;
        this.last_chat = last_chat;
        this.last_time = last_time;
    }

    public void changeCount(int count){
        this.count = count;
    }

    public void changeLastChat(String chat){this.last_chat = chat;}

    public void changeLastTime(){this.last_time = LocalDateTime.now();}


}
