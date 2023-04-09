package com.example.Waffle.service;

import com.example.Waffle.dto.GroupDto;
import com.example.Waffle.dto.UserGroupDto;
import com.example.Waffle.entity.GroupEntity;
import com.example.Waffle.entity.UserEntity;
import com.example.Waffle.entity.UserGroup.UserGroupEntity;
import com.example.Waffle.exception.ErrorCode;
import com.example.Waffle.exception.UserException;
import com.example.Waffle.repository.GroupRepository;
import com.example.Waffle.repository.UserGroupRepository;
import com.example.Waffle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Transactional
    public String userGrouplist(String email){
        UserEntity userEntity = userRepository.findByemail(email)
                .orElseThrow(() -> new UserException(ErrorCode.NO_USER));


        JSONObject result = new JSONObject();
        try{
            List<UserGroupEntity> userGroupEntities = userGroupRepository.findByUserId(userEntity.getId());
            JSONArray jsonArr = new JSONArray();
            for(UserGroupEntity userGroupEntity : userGroupEntities){
                JSONObject json = new JSONObject();
                GroupEntity groupEntity = userGroupEntity.getGroup();
                json.put("group_name", groupEntity.getName());
                json.put("group_id", groupEntity.getId());
                json.put("manager", userGroupEntity.getManager());
                jsonArr.put(json);
            }
            result.put("groups", jsonArr);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }



}
