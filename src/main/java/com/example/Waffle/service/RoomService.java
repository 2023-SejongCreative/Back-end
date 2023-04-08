package com.example.Waffle.service;


import com.example.Waffle.dto.RoomDto;
import com.example.Waffle.entity.GroupEntity;
import com.example.Waffle.entity.UserEntity;
import com.example.Waffle.exception.ErrorCode;
import com.example.Waffle.exception.UserException;
import com.example.Waffle.repository.GroupRepository;
import com.example.Waffle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    @Transactional
    public void createRoom(RoomDto roomDto, String email, int groupId){

        //groupId로 group 정보 찾기
        GroupEntity groupEntity = groupRepository.findById(groupId).orElseThrow(
                () -> new UserException(ErrorCode.NO_GROUP)
        );

        //email로 user 정보 찾기
        UserEntity userEntity = userRepository.findById(email).orElseThrow(
                () -> new UserException(ErrorCode.NO_USER)
        );





    }
}
