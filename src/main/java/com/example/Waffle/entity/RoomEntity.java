package com.example.Waffle.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @ManyToOne
    @Column(name = "group_id")
    private int groupId;

    @Builder
    public RoomEntity(String name, int type, int groupId){
        this.name = name;
        this.type = type;
        this.groupId = groupId;
    }

}
