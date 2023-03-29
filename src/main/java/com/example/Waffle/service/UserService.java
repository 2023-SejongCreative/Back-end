package com.example.Waffle.service;

import com.example.Waffle.dto.LoginDto;
import com.example.Waffle.dto.ResponseDto;
import com.example.Waffle.dto.TokenDto;
import com.example.Waffle.dto.UserDto;
import com.example.Waffle.entity.TokenEntity;
import com.example.Waffle.entity.UserEntity;
import com.example.Waffle.repository.TokenRepository;
import com.example.Waffle.token.JwtTokenProvider;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import com.example.Waffle.exception.ErrorCode;
import com.example.Waffle.exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.Waffle.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;


    //회원가입 처리
    @Transactional
    public void save(UserDto userDto){
        //아이디 중복 확인
       if(userRepository.existsByemail(userDto.getEmail())) {
           throw new UserException(ErrorCode.DUPLICATE_ID);
       }

        //DB 저장
        this.userRepository.save(userDto.toEntity());
    }

    //로그인 처리
    public void login(LoginDto loginDto, HttpServletResponse response){
        //존재하는 유저인지 확인
        UserEntity userEntity = userRepository.findByemail(loginDto.getEmail()).orElseThrow(
                () -> new UserException(ErrorCode.NO_USER)
        );

        //비밀번호가 올바른지 확인
        if(!passwordEncoder.matches(loginDto.getPassword(), userEntity.getPassword())){
            throw new UserException(ErrorCode.NO_PASSWORD);
        }

        //ATK, RTK 모두 발급
        TokenDto tokenDto = jwtTokenProvider.createAllToken(loginDto.getEmail());

        // Refresh토큰 DB에 있는지 확인
        Optional<TokenEntity> tokenEntity = tokenRepository.findByEmail(loginDto.getEmail());

        // 있다면 새토큰 발급후 업데이트
        if(tokenEntity.isPresent()) {
            tokenRepository.save(tokenEntity.get().updateToken(tokenDto.getRefreshToken()));
        }else {// 없다면 새로 만들고 디비 저장
            TokenEntity newToken = new TokenEntity(tokenDto.getRefreshToken(), loginDto.getEmail());
            tokenRepository.save(newToken);
        }

        // response 헤더에 Access Token / Refresh Token 넣음
        setHeader(response, tokenDto);

    }

    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader("Access_Token", tokenDto.getAccessToken());
        response.addHeader("Refresh_Token", tokenDto.getRefreshToken());
    }




}
