package com.example.Waffle.service;

import com.example.Waffle.dto.LoginDto;
import com.example.Waffle.dto.UserDto;
import com.example.Waffle.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import com.example.Waffle.exception.ErrorCode;
import com.example.Waffle.exception.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.Waffle.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //회원가입 처리
    public void register(UserEntity userEntity) {

        this.userRepository.save(userEntity);
    }


    //로그인 처리

    @Transactional
    public void save(UserDto UserDto){
        //아이디 중복 확인
       if(userRepository.existsByemail(UserDto.getEmail())) {
           throw new UserException(ErrorCode.DUPLICATE_ID);
       }
       String encodedPassword = passwordEncoder.encode(UserDto.getPassword());
       UserEntity userEntity = new UserEntity(UserDto.getEmail(), encodedPassword, UserDto.getName());

        //DB 저장
        this.userRepository.save(userEntity);
    }

    //로그인 처리
    public UserEntity login(LoginDto loginDto){
        //존재하는 유저인지 확인
        UserEntity userEntity = userRepository.findByemail(loginDto.getEmail()).orElseThrow(
                () -> new UserException(ErrorCode.NO_USER)
        );


        //비밀번호가 올바른지 확인
        if(!passwordEncoder.matches(userEntity.getPassword(), loginDto.getPassword())){
            throw new UserException(ErrorCode.NO_PASSWORD);
        }
        return userEntity;
    }
}
