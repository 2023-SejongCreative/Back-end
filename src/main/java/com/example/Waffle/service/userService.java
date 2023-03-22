package com.example.Waffle.service;

import com.example.Waffle.dto.loginDto;
import com.example.Waffle.entity.userEntity;
import com.example.Waffle.exception.errorCode;
import com.example.Waffle.exception.userException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Waffle.repository.userRepository;


@Service
@RequiredArgsConstructor
public class userService {

    private final userRepository userRepository;

    //로그인 처리
    public userEntity login(loginDto loginDto){
        userEntity userEntity = userRepository.findByemail(loginDto.getEmail()).orElseThrow(
                () -> new userException(errorCode.NO_USER)
        );

        if(!loginDto.getPassword().equals(userEntity.getPassword())){
            throw new userException(errorCode.NO_PASSWORD);
        }
        return userEntity;
    }
}
