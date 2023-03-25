package com.example.Waffle.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginDto {
    private String email;
    private String password;

    public LoginDto(String email, String password){
        this.email = email;
        this.password = password;
    }
}
