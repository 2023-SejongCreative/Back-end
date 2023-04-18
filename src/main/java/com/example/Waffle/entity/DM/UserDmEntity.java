package com.example.Waffle.entity.DM;


import com.example.Waffle.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
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

    @OneToMany(mappedBy = "dm")
    private List<MessageEntity> dmId = new ArrayList<>();

//    @OneToMany(mappedBy = "user")
//    private List<MessageEntity> userId = new ArrayList<>();

}
