package com.example.Waffle.service;

import com.example.Waffle.dto.LoginDto;
import com.example.Waffle.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import com.example.Waffle.exception.ErrorCode;
import com.example.Waffle.exception.UserException;
import org.springframework.stereotype.Service;
import com.example.Waffle.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    
    //회원가입 처리
    public void register(UserEntity userEntity) {

        this.userRepository.save(userEntity);
    }

    //로그인 처리
    public UserEntity login(LoginDto loginDto){
        UserEntity userEntity = userRepository.findByemail(loginDto.getEmail()).orElseThrow(
                () -> new UserException(ErrorCode.NO_USER)
        );

        if(!loginDto.getPassword().equals(userEntity.getPassword())){
            throw new UserException(ErrorCode.NO_PASSWORD);
        }
        return userEntity;
    }
}
