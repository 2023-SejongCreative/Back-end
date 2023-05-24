package com.example.Waffle.entity.UserGroup;

import com.example.Waffle.entity.GroupEntity;
import com.example.Waffle.entity.UserEntity;
import jakarta.persistence.IdClass;
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
