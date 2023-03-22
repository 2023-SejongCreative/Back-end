package com.example.Waffle.dto;

import com.example.Waffle.entity.userEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class userDto {
    private Long id;
    private String email;
    private String password;
    private String name;
}
