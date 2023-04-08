package com.example.Waffle.entity.UserGroup;

import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupPK implements Serializable {
    private String user;
    private String group;
}
