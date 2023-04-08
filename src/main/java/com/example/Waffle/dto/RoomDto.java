package com.example.Waffle.dto;


import com.example.Waffle.entity.GroupEntity;
import com.example.Waffle.entity.RoomEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoomDto {

    private String name;
    private int type;

    public RoomDto(String name, int type){
        this.name = name;
        this.type = type;
    }

    public RoomEntity toEntity(GroupEntity groupId){
        RoomEntity roomEntity = new RoomEntity().builder()
                .name(name)
                .type(type)
                .groupId(groupId)
                .build();

        return roomEntity;
    }
}
