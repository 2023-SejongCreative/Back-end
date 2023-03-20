package service;

import dto.loginDto;
import dto.userDto;
import entity.userEntity;
import exception.errorCode;
import exception.userException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.userRepository;


@Service
public class userService {

    private final userRepository userRepository;

    @Autowired
    public userService(userRepository userRepository) {
        this.userRepository = userRepository;
    }

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
