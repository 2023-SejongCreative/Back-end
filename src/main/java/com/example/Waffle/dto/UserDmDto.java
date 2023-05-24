package com.example.Waffle.dto;

import com.example.Waffle.entity.DM.DmEntity;
import com.example.Waffle.entity.DM.UserDmEntity;
import com.example.Waffle.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDmDto {

    private UserEntity user;
    private DmEntity dm;

    public UserDmEntity toEntity(){
        UserDmEntity userDmEntity = new UserDmEntity().builder()
                .user(user)
                .dm(dm)
                .build();
        return  userDmEntity;
    }
}
