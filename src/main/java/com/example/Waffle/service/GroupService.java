package com.example.Waffle.service;

import com.example.Waffle.dto.GroupDto;
import com.example.Waffle.dto.UserGroupDto;
import com.example.Waffle.entity.Group.GroupEntity;
import com.example.Waffle.entity.Room.RoomEntity;
import com.example.Waffle.entity.UserEntity;
import com.example.Waffle.entity.Group.UserGroupEntity;
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
    private final RoomService roomService;
    private final PlanService planService;
    private final NoteService noteService;
    @Transactional
    public Long createGroup(String email, GroupDto groupDto){

        UserEntity userEntity = userRepository.findByemail(email)
                .orElseThrow(() -> new UserException(ErrorCode.NO_USER));


        GroupEntity groupEntity = groupDto.toEntity();
        this.groupRepository.save(groupEntity);

        UserGroupDto userGroupDto = new UserGroupDto(userEntity, groupEntity, 1);
        userGroupRepository.save(userGroupDto.toEntity());

        return groupEntity.getId();
    }

    @Transactional
    public String userGroupList(String email){
        UserEntity userEntity = userRepository.findByemail(email)
                .orElseThrow(() -> new UserException(ErrorCode.NO_USER));


        JSONObject groupList = new JSONObject();
        try{
            List<UserGroupEntity> userGroupEntities = userGroupRepository.findByUserId(userEntity.getId());
            JSONArray groupArr = new JSONArray();
            for(UserGroupEntity userGroupEntity : userGroupEntities){
                JSONObject group = new JSONObject();
                GroupEntity groupEntity = userGroupEntity.getGroup();
                group.put("group_name", groupEntity.getName());
                group.put("group_id", groupEntity.getId());
                group.put("manager", userGroupEntity.getManager());
                groupArr.put(group);
            }
            groupList.put("groups", groupArr);
        }catch (Exception e){
            throw new UserException(ErrorCode.CANT_FINDGROUP);
        }
        return groupList.toString();
    }

    @Transactional
    public void inviteUser(Long groupId, String email){

        UserEntity userEntity = userRepository.findByemail(email)
                .orElseThrow(() -> new UserException(ErrorCode.NO_USER));

        GroupEntity groupEntity = groupRepository.findById(groupId)
                .orElseThrow(() -> new UserException(ErrorCode.NO_GROUP));


        Optional<UserGroupEntity> userGroupEntity = userGroupRepository.findByUserIdAndGroupId(userEntity.getId(), groupEntity.getId());
        if(userGroupEntity.isPresent()){
            throw new UserException(ErrorCode.DUPLICATE_GROUP_USER);
        }
        else{
            UserGroupDto userGroupDto = new UserGroupDto(userEntity, groupEntity, 0);
            userGroupRepository.save(userGroupDto.toEntity());
        }


    }

    @Transactional
    public void deleteGroup(Long groupId){

        try {
            GroupEntity groupEntity = groupRepository.findById(groupId)
                    .orElseThrow(() -> new UserException(ErrorCode.NO_GROUP));

            //group의 plan 전부 삭제
            planService.allDeletePlan("group", groupEntity.getId());

            //group의 note 전부 삭제
            noteService.deleteAllNote("group", groupEntity.getId());

            //group의 room 전부 삭제
            roomService.deleteAllRoom(groupEntity);

            userGroupRepository.deleteByGroupId(groupId);
            groupRepository.deleteById(groupId);

        }catch(Exception e){
            throw new UserException(ErrorCode.INTER_SERVER_ERROR);
        }

    }



}
