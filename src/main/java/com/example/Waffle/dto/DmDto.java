package com.example.Waffle.dto;

import com.example.Waffle.entity.DM.DmEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DmDto {

    private String name;
    private String last_chat;
    private LocalDateTime last_time;
    private int count;

    public DmDto(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public DmEntity toEntity(){
        new DmEntity();
        DmEntity dmEntity = DmEntity.builder().
                name(name).
                count(count).
                last_chat(last_chat).
                last_time(last_time).
                build();
        return dmEntity;
    }


}
