package com.example.Waffle.entity.DM;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String chat;
    private LocalDateTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_dm_dm_id")
    private UserDmEntity dm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_dm_user_id")
    private  UserDmEntity user;


}
