package com.example.Waffle.controller;

import com.example.Waffle.dto.ResponseDto;
import com.example.Waffle.dto.UserDto;
import com.example.Waffle.dto.LoginDto;
import com.example.Waffle.entity.UserEntity;
import com.example.Waffle.token.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.Waffle.service.UserService;

import java.util.Map;


@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final HttpServletResponse response;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<String> register(@RequestBody Map<String, String> param){

        UserDto userDto = new UserDto(param.get("email"),
                passwordEncoder.encode(param.get("password")),
                param.get("name"));

        userService.save(userDto);

        return ResponseEntity.ok("회원가입에 성공하였습니다.");
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Object> login(@RequestBody Map<String, String> param){

        LoginDto loginDto = new LoginDto(param.get("email"),
                param.get("password"));

        userService.login(loginDto, response);

        return new ResponseEntity<>("로그인에 성공하였습니다.", HttpStatus.OK);
    }

    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<Object> logout(@RequestHeader("refresh_token") String refreshToken,
                                         @RequestHeader("access_token") String accessToken) {

        // 헤더에서 JWT 토큰 받아오기
        //String accessToken = jwtTokenProvider.resolveToken((HttpServletRequest) request, "Access_Token");
        //String refreshToken = jwtTokenProvider.resolveToken((HttpServletRequest) request, "Refresh_Token");

        userService.logout(refreshToken, accessToken, response);

        return new ResponseEntity<>("로그아웃에 성공하였습니다.", HttpStatus.OK);
    }

    @PostMapping("/reissue")
    @ResponseBody
    public ResponseEntity<Object> reissue(@RequestHeader("refresh_token") String refreshToken,
                                          @RequestHeader("access_token") String accessToken){

//        // 헤더에서 JWT 토큰 받아오기
//        String accessToken = jwtTokenProvider.resolveToken((HttpServletRequest) request, "access_token");
//        String refreshToken = jwtTokenProvider.resolveToken((HttpServletRequest) request, "refresh_token");

        userService.reissue(refreshToken, accessToken, response);

        return new ResponseEntity<>("엑세스 토큰 재발급이 되었습니다.", HttpStatus.OK);
    }
}
