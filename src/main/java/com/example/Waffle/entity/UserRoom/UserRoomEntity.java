package com.example.Waffle.entity.UserRoom;

import com.example.Waffle.entity.RoomEntity;
import com.example.Waffle.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

@Getter
@Entity
@NoArgsConstructor
@IdClass(UserRoomPK.class)
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

    //관리자이면(1) 아니면(0)
    private int manager;

    @Builder
    public UserRoomEntity(UserEntity user, RoomEntity room, int manager){
        this.user = user;
        this.room = room;
        this.manager = manager;
    }

}
