package com.example.Waffle.dto;

import com.example.Waffle.entity.GroupEntity;
import com.example.Waffle.entity.PlanEntity;
import com.example.Waffle.entity.RoomEntity;
import com.example.Waffle.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanDto {
    private String title;
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer state;

    private UserEntity user;
    private GroupEntity group;
    private RoomEntity room;

    public PlanDto(String title, String content, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void stringToIntState(String state){
        if(state.equals("미완료"))
            this.state = 0;
        else if(state.equals("완료"))
            this.state = 1;
        else if(state.equals("진행중"))
            this.state = 2;
        else this.state = null;
    }


    public PlanEntity toEntity(){
        new PlanEntity();
        return PlanEntity.builder().
                title(title).
                content(content).
                startDate(startDate).
                endDate(endDate).
                state(state).
                user(user).
                group(group).
                room(room).
                build();
    }

}
