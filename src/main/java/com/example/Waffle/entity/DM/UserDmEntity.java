package com.example.Waffle.entity.DM;


import com.example.Waffle.entity.GroupEntity;
import com.example.Waffle.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="user_dm")
@IdClass(UserDmPK.class)
public class UserDmEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private UserEntity user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="dm_id")
    private DmEntity dm;

    @Builder
    public UserDmEntity(UserEntity user, DmEntity dm){
        this.user = user;
        this.dm = dm;
    }



}
