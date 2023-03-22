package com.example.Waffle.service;

import com.example.Waffle.dto.userDto;
import com.example.Waffle.entity.userEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.Waffle.repository.userRepository;

@Service
@RequiredArgsConstructor
public class userService {
    private final userRepository userRepository;

    public void register(userEntity userEntity) {
        this.userRepository.save(userEntity);
    }
}
