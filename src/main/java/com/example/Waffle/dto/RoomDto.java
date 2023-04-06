package com.example.Waffle.dto;


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
}
