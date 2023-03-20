package service;

import dto.userDto;
import entity.userEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.userRepository;

@Service
@RequiredArgsConstructor
public class userService {
    private final userRepository userRepository;

    public void register(userDto userDto) {
        //
        userEntity userEntity = userDto.toEntity();
        userRepository.save(userEntity);
    }
}
