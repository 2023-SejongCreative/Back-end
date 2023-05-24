package com.example.Waffle.dto;

import com.example.Waffle.entity.Group.GroupEntity;
import com.example.Waffle.entity.PlanEntity;
import com.example.Waffle.entity.Room.RoomEntity;
import com.example.Waffle.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    public PlanDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void stringToIntState(String state){
        if(state !=null) {
            if (state.equals("미완료"))
                this.state = 0;
            else if (state.equals("완료"))
                this.state = 1;
            else if (state.equals("진행중"))
                this.state = 2;
            else this.state = null;
        }
        else this.state = null;
    }

    public void setStartDate(String startDate){
        if(startDate == null || startDate.equals(""))
            this.startDate = null;
        else
            this.startDate = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
    }

    public void setEndDate(String endDate){
        if(endDate == null || endDate.equals(""))
            this.endDate = null;
        else
            this.endDate = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);
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
