package com.example.Waffle.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "user_room")
public class UserRoomEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private RoomEntity room;

    private int manager;

    @Builder
    public UserRoomEntity(UserEntity user, RoomEntity room, int manager){
        this.user = user;
        this.room = room;
        this.manager = manager;
    }

}
