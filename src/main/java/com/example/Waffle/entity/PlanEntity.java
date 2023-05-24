package com.example.Waffle.entity;

import com.example.Waffle.entity.Group.GroupEntity;
import com.example.Waffle.entity.Room.RoomEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "plan")
public class PlanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private LocalDate start_date;
    private LocalDate end_date;
    private Integer state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private GroupEntity group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private RoomEntity room;


    public void changeTitle(String title){this.title = title;}
    public void changeContent(String content){this.content = content;}
    public void changeStartDate(LocalDate startDate){this.start_date = startDate;}
    public void changeEndDate(LocalDate endDate){this.end_date = endDate;}
    public void changeState(Integer state){this.state = state;}


    @Builder
    public PlanEntity(String title, String content, LocalDate startDate, LocalDate endDate, Integer state, UserEntity user, GroupEntity group, RoomEntity room){
        this.title = title;
        this.content = content;
        this.start_date = startDate;
        this.end_date = endDate;
        this.state = state;
        this.user = user;
        this.group =group;
        this.room = room;
    }


}
