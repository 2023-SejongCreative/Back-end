package com.example.Waffle.dto;

import com.example.Waffle.entity.UserContentEntity;
import com.example.Waffle.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserContentDto {
    private String title;
    private String detail;
    private Long userId;
    private UserEntity user;

    public UserContentEntity toEntity(){
        UserContentEntity userContentEntity = new UserContentEntity().builder()
                .title(title)
                .detail(detail)
                .user(user)
                .build();
        return userContentEntity;
    }


}
