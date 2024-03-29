package com.example.Waffle.service;


import com.example.Waffle.dto.RoomDto;
import com.example.Waffle.dto.UserRoomDto;
import com.example.Waffle.entity.Group.GroupEntity;
import com.example.Waffle.entity.Group.UserGroupEntity;
import com.example.Waffle.entity.Room.RoomEntity;
import com.example.Waffle.entity.UserEntity;
import com.example.Waffle.entity.Room.UserRoomEntity;
import com.example.Waffle.exception.ErrorCode;
import com.example.Waffle.exception.UserException;
import com.example.Waffle.repository.*;
import com.example.Waffle.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final RoomRepository roomRepository;
    private final UserRoomRepository userRoomRepository;
    private final UserGroupRepository userGroupRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PlanService planService;
    private final NoteService noteService;

    @Transactional
    public Long createRoom(RoomDto roomDto, String accessToken, Long groupId){
        // Access Token 에서 User email 을 가져오기
        String email = jwtTokenProvider.getEmail(accessToken);

        //groupId로 group 정보 찾기
        GroupEntity groupEntity = groupRepository.findById(groupId).orElseThrow(
                () -> new UserException(ErrorCode.NO_GROUP)
        );

        //email로 user 정보 찾기
        UserEntity userEntity = userRepository.findByemail(email).orElseThrow(
                () -> new UserException(ErrorCode.NO_USER)
        );

        //room 정보 db에 저장
        RoomEntity roomEntity = roomDto.toEntity(groupEntity);
        this.roomRepository.save(roomEntity);

        //user_room mapping table에 정보 저장
        UserRoomDto userRoomDto = new UserRoomDto(userEntity, roomEntity, 1);
        UserRoomEntity userRoomEntity = userRoomDto.toEntity();
        this.userRoomRepository.save(userRoomEntity);

        return roomEntity.getId();
    }

    public String roomList(String accessToken, Long groupId){

        // Access Token 에서 User email 을 가져오기
        String email = jwtTokenProvider.getEmail(accessToken);

        //email로 user 정보 찾기
        UserEntity userEntity = userRepository.findByemail(email).orElseThrow(
                () -> new UserException(ErrorCode.NO_USER)
        );

        //groupId로 group 정보 찾기
        GroupEntity groupEntity = groupRepository.findById(groupId).orElseThrow(
                () -> new UserException(ErrorCode.NO_GROUP)
        );

        JSONObject roomList = new JSONObject();
        try{
            //groupId로 해당 group의 룸 조회
            List<RoomEntity> roomEntities = roomRepository.findAllByGroup(groupEntity);

            JSONArray roomArr = new JSONArray();

            //룸 목록 JSON 객체에 저장
            for(RoomEntity roomEntity : roomEntities){
                JSONObject room = new JSONObject();

                //UserRoom에서 사용자가 해당 룸에 초대된 사람인지 조회
                Optional<UserRoomEntity> userRoomEntity = userRoomRepository.findByUserIdAndRoomId(userEntity.getId(), roomEntity.getId());
                if(userRoomEntity.isPresent()){ //초대되었으면
                    room.put("room_name", roomEntity.getName());
                    room.put("room_id", roomEntity.getId());
                    room.put("type", roomEntity.getType());
                    room.put("manager", userRoomEntity.get().getManager());
                    room.put("include", 1);
                    roomArr.put(room);
                }
            }
            roomList.put("room", roomArr);

        }catch(Exception e){
            throw new UserException(ErrorCode.CANT_FINDROOM);
        }

        return roomList.toString();
    }

    @Transactional
    public void inviteUser(Long roomId, String email){

        //email로 user 정보 찾기
        UserEntity userEntity = userRepository.findByemail(email).orElseThrow(
                () -> new UserException(ErrorCode.NO_USER)
        );

        //roomId로 room 정보 찾기
        RoomEntity roomEntity = roomRepository.findById(roomId).orElseThrow(
                () -> new UserException(ErrorCode.NO_ROOM)
        );

        Optional<UserGroupEntity> userGroupEntity = userGroupRepository.findByUserIdAndGroupId(userEntity.getId(), roomEntity.getGroup().getId());
        if(userGroupEntity.isEmpty()){
            throw new UserException(ErrorCode.CANT_FIND_GROUPUSER);
        }

        //UserRoom에서 사용자가 해당 룸에 초대된 사람인지 조회
        Optional<UserRoomEntity> userRoom = userRoomRepository.findByUserIdAndRoomId(userEntity.getId(), roomEntity.getId());
        if(userRoom.isPresent()){ //초대되었으면
            throw new UserException(ErrorCode.DUPLICATE_ROOM_USER);
        }
        else{ //초대 안되었으면
            //user_room mapping table에 정보 저장
            UserRoomDto userRoomDto = new UserRoomDto(userEntity, roomEntity, 0);
            UserRoomEntity userRoomEntity = userRoomDto.toEntity();
            this.userRoomRepository.save(userRoomEntity);
        }

    }

    @Transactional
    public void deleteRoom(Long roomId, String type){

        //roomId로 room 정보 찾기
        RoomEntity roomEntity = roomRepository.findById(roomId).orElseThrow(
                () -> new UserException(ErrorCode.NO_ROOM)
        );

        if(!type.equals("group")){

            String email = jwtTokenProvider.getEmail(type);

            //email로 user 정보 찾기
            UserEntity userEntity = userRepository.findByemail(email).orElseThrow(
                    () -> new UserException(ErrorCode.NO_USER)
            );
            //UserRoom에서 사용자가 해당 룸에 초대된 사람인지 조회
            UserRoomEntity userRoom = userRoomRepository.findByUserIdAndRoomId(userEntity.getId(), roomEntity.getId())
                    .orElseThrow(() -> new UserException(ErrorCode.BAD_REQUEST));

            if(userRoom.getManager() == 0){
                throw new UserException(ErrorCode.NO_MANAGER);
            }
        }

        try {

            //room에 속한 plan 모두 삭제
            planService.allDeletePlan("room", roomId);

            //room에 속한 note 모두 삭제
            noteService.deleteAllNote("room", roomId);

            userRoomRepository.deleteByRoomId(roomEntity.getId());

            roomRepository.deleteById(roomEntity.getId());

        }catch(Exception e){
            throw new UserException(ErrorCode.INTER_SERVER_ERROR);
        }

    }

    @Transactional
    public void deleteAllRoom(GroupEntity groupEntity){

        try {
            List<RoomEntity> roomEntities = roomRepository.findAllByGroup(groupEntity);

            for (RoomEntity roomEntity : roomEntities) {
                deleteRoom(roomEntity.getId(), "group");
            }
        }catch(Exception e){
            throw new UserException(ErrorCode.INTER_SERVER_ERROR);
        }
    }
}
