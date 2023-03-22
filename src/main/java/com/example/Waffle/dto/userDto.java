package com.example.Waffle.dto;

import com.example.Waffle.entity.userEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class userDto {
    private int id;
    private String email;
    private String password;
    private String name;

    public userDto(String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public userEntity toEntity(){
        userEntity userEntity = new userEntity().builder()
                .email(email)
                .password(password)
                .name(name)
                .build();
        return userEntity;
    }

}
