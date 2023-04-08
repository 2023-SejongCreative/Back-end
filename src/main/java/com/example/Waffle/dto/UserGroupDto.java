package com.example.Waffle.dto;


import com.example.Waffle.entity.GroupEntity;
import com.example.Waffle.entity.UserEntity;
import com.example.Waffle.entity.UserGroup.UserGroupEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupDto {
    private UserEntity user;
    private GroupEntity group;
    private int manager;

    public UserGroupEntity toEntity(){
        UserGroupEntity userGroupEntity = new UserGroupEntity().builder()
                .user(user)
                .group(group)
                .manager(manager)
                .build();
        return  userGroupEntity;
    }


}
