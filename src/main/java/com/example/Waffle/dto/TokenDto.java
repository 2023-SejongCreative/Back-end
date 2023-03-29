package com.example.Waffle.dto;

import com.example.Waffle.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.el.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@NoArgsConstructor
public class TokenDto {
    private String refreshToken;
    private String accessToken;

    public TokenDto(String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public TokenDto updateToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

}
