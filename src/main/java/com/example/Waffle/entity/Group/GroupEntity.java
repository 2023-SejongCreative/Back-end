package com.example.Waffle.entity.Group;

import com.example.Waffle.entity.NoteEntity;
import com.example.Waffle.entity.PlanEntity;
import com.example.Waffle.entity.Room.RoomEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "w_group")
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "group")
    private List<UserGroupEntity> userGroup = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    private List<RoomEntity> room = new ArrayList<>();


    @OneToMany(mappedBy = "group")
    private List<PlanEntity> plan = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    private List<NoteEntity> note = new ArrayList<>();

    @Builder
    public GroupEntity(String name){
        this.name = name;
    }



}
