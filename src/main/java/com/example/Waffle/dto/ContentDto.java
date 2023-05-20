package com.example.Waffle.dto;

import com.example.Waffle.entity.UserContentEntity;
import com.example.Waffle.entity.UserEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ContentDto {
    private String title;
    private String detail;
    private UserEntity user;

    public UserContentEntity toEntity(){
        return UserContentEntity.builder()
                .title(title)
                .detail(detail)
                .user(user)
                .build();
    }
}
