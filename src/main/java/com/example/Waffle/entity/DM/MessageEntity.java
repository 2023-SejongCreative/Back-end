package com.example.Waffle.entity.DM;

import com.example.Waffle.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "message")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dm_id")
    private DmEntity dm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;


    private String chat;
    private LocalDateTime time;

    public String getUserName(){
        return user.getName();
    }

    public String getUserEmail(){
        return user.getEmail();
    }

    @Builder
    public MessageEntity(DmEntity dm, UserEntity user, String chat, LocalDateTime time){
        this.dm = dm;
        this.user = user;
        this.chat = chat;
        this.time = time;
    }


}
