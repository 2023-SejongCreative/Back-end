package com.example.Waffle.dto;


import com.example.Waffle.entity.GroupEntity;
import com.example.Waffle.entity.RoomEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {

    private String name;
    private int type;


    public RoomEntity toEntity(GroupEntity groupId){
        RoomEntity roomEntity = new RoomEntity().builder()
                .name(name)
                .type(type)
                .group(groupId)
                .build();

        return roomEntity;
    }
}
