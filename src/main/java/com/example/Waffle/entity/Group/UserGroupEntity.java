package com.example.Waffle.entity.Group;


import com.example.Waffle.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Data
@Table(name="user_group")
@IdClass(UserGroupPK.class)
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
    public UserGroupEntity(UserEntity user, GroupEntity group, int manager){
        this.user = user;
        this.group = group;
        this.manager = manager;
    }


}
