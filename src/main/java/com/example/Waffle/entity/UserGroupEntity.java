package com.example.Waffle.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name="user_group")
public class UserGroupEntity {


    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private GroupEntity group;

    private int manager;

    @Builder
    public UserGroupEntity(int manager){
        this.manager = manager;
    }


}
