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
@Table(name = "room")
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    //private = 0 / public = 1
    private int type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private GroupEntity groupId;

    @OneToMany(mappedBy = "room")
    private List<UserRoomEntity> userRoom  = new ArrayList<>();

    @Builder
    public RoomEntity(String name, int type, GroupEntity groupId){
        this.name = name;
        this.type = type;
        this.groupId = groupId;
    }

}
