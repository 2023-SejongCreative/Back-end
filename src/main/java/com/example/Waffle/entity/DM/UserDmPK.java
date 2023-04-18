package com.example.Waffle.entity.DM;

import com.example.Waffle.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDmPK implements Serializable {
    private DmEntity dm;
    private UserEntity user;
}
