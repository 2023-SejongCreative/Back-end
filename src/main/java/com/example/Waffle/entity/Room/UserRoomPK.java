package com.example.Waffle.entity.Room;

import com.example.Waffle.entity.Room.RoomEntity;
import com.example.Waffle.entity.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class UserRoomPK implements Serializable {
    private UserEntity user;
    private RoomEntity room;
}
