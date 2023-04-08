package com.example.Waffle.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "group")
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "group")
    private List<UserGroupEntity> userGroup = new ArrayList<>();

    @OneToMany(mappedBy = "groupId")
    private List<RoomEntity> room = new ArrayList<>();

    @Column(name = "name")
    private String name;


    @Builder
    public GroupEntity(String name){
        this.name = name;
    }



}
