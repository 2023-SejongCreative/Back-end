package com.example.Waffle.dto;

import com.example.Waffle.entity.Room.RoomEntity;
import com.example.Waffle.entity.UserEntity;
import com.example.Waffle.entity.Room.UserRoomEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRoomDto {

    private UserEntity user;
    private RoomEntity room;
    int manager;

    public UserRoomEntity toEntity(){
        UserRoomEntity userRoomEntity = new UserRoomEntity().builder()
                .user(user)
                .room(room)
                .manager(manager)
                .build();

        return userRoomEntity;
    }

}
