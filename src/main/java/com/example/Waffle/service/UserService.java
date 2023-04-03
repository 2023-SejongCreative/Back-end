package com.example.Waffle.service;

import com.example.Waffle.dto.LoginDto;
import com.example.Waffle.dto.TokenDto;
import com.example.Waffle.dto.UserDto;
import com.example.Waffle.entity.UserEntity;
import com.example.Waffle.token.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import com.example.Waffle.exception.ErrorCode;
import com.example.Waffle.exception.UserException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.Waffle.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;


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

    @Transactional
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

        // RefreshToken Redis에 저장 -> expirationTime 설정을 통해 자동 삭제 처리
        redisTemplate.opsForValue()
                .set("RT:" + loginDto.getEmail(), tokenDto.getRefreshToken(), tokenDto.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        // response 헤더에 Access Token / Refresh Token 넣음
        setHeader(response, tokenDto);

    }

    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader("Access_Token", tokenDto.getAccessToken());
        response.addHeader("Refresh_Token", tokenDto.getRefreshToken());
    }

    @Transactional
    public void logout(String refreshToken, String accessToken, HttpServletResponse response){

        // Access Token 확인
        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new UserException(ErrorCode.EXPIRATION_ATK);
        }

        // Access Token 에서 User email 을 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);

        // Redis 에서 해당 User email 로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제
        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
            // Refresh Token 삭제
            redisTemplate.delete("RT:" + authentication.getName());
        }

        // 해당 Access Token 유효시간 가지고 와서 BlackList 로 저장하기
        Long expiration = jwtTokenProvider.getExpiration(accessToken);
        redisTemplate.opsForValue().set(accessToken, "logout", expiration, TimeUnit.MILLISECONDS);
    }


    @Transactional
    public void reissue(String refreshToken, String accessToken, HttpServletResponse response){

        // Refresh Token 확인
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new UserException(ErrorCode.UNAUTHORIZED);
        }

        // 토큰으로부터 유저 정보를 받아서 저장
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);

        // Redis 에서 User email 을 기반으로 저장된 Refresh Token 값을 가져옵니다.
        String redisRefreshToken = (String)redisTemplate.opsForValue().get("RT:" + authentication.getName());

        // 로그아웃되어 Redis 에 RefreshToken 이 존재하지 않는 경우
        if(ObjectUtils.isEmpty(refreshToken)) {
            throw new UserException(ErrorCode.UNAUTHORIZED);
        }
        if(!redisRefreshToken.equals(refreshToken)) {
            throw new UserException(ErrorCode.UNAUTHORIZED);
        }

        //새로운 ATK, RTK 모두 발급
        TokenDto tokenDto = jwtTokenProvider.createAllToken(authentication.getName());

        // RefreshToken Redis 업데이트
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), tokenDto.getRefreshToken(), tokenDto.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        // response 헤더에 Access Token / Refresh Token 넣음
        setHeader(response, tokenDto);
    }



}
