package com.example.Waffle.dto;


import com.example.Waffle.entity.GroupEntity;
import com.example.Waffle.entity.UserGroupEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupDto {
    private int user_id;
    private int group_id;
    private int manager;

    public UserGroupEntity toEntity(){
        UserGroupEntity userGroupEntity = new UserGroupEntity().builder()
                .manager(manager)
                .build();
        return  userGroupEntity;
    }


}
