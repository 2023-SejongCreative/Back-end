package com.example.Waffle.service;

import com.example.Waffle.dto.LoginDto;
import com.example.Waffle.dto.UserDto;
import com.example.Waffle.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import com.example.Waffle.exception.ErrorCode;
import com.example.Waffle.exception.UserException;
import org.springframework.stereotype.Service;
import com.example.Waffle.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;


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
    public boolean checkEmailDuplicate(String email){
        return userRepository.existsByemail(email);
    }
    @Transactional
    public void save(UserDto userDto){
        //아이디 중복 확인
       if(userRepository.existsByemail(userDto.getEmail())) {
           throw new UserException(ErrorCode.DUPLICATE_ID);
       }

        //여기서 비밀번호 바꿔주면 됨

        //DB 저장
        this.userRepository.save(userDto.toEntity());
    }

    //로그인 처리
    public UserEntity login(LoginDto loginDto){
        //존재하는 유저인지 확인
        UserEntity userEntity = userRepository.findByemail(loginDto.getEmail()).orElseThrow(
                () -> new UserException(ErrorCode.NO_USER)
        );


        //비밀번호가 올바른지 확인
        if(!loginDto.getPassword().equals(userEntity.getPassword())){
            throw new UserException(ErrorCode.NO_PASSWORD);
        }
        return userEntity;
    }
}
