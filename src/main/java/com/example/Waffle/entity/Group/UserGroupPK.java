package com.example.Waffle.entity.Group;

import com.example.Waffle.entity.Group.GroupEntity;
import com.example.Waffle.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupPK implements Serializable {
    private UserEntity user;
    private GroupEntity group;
}
