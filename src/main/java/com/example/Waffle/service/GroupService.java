package com.example.Waffle.service;

import com.example.Waffle.dto.GroupDto;
import com.example.Waffle.dto.UserGroupDto;
import com.example.Waffle.entity.GroupEntity;
import com.example.Waffle.entity.UserEntity;
import com.example.Waffle.exception.ErrorCode;
import com.example.Waffle.exception.UserException;
import com.example.Waffle.repository.GroupRepository;
import com.example.Waffle.repository.UserGroupRepository;
import com.example.Waffle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;
    @Transactional
    public void createGroup(String email, GroupDto groupDto){

        UserEntity userEntity = userRepository.findByemail(email)
                .orElseThrow(() -> new UserException(ErrorCode.NO_USER));


        GroupEntity groupEntity = groupDto.toEntity();
        this.groupRepository.save(groupEntity);

        UserGroupDto userGroupDto = new UserGroupDto(userEntity, groupEntity, 1);
        userGroupRepository.save(userGroupDto.toEntity());

    }

}
