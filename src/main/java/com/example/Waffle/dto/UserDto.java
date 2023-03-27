package com.example.Waffle.dto;

import com.example.Waffle.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private String name;

    public UserDto(String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public UserEntity toEntity(){
        UserEntity userEntity = new UserEntity().builder()
                .email(email)
                .password(password)
                .name(name)
                .build();
        return userEntity;
    }

}
