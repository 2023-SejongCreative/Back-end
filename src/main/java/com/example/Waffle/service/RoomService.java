package com.example.Waffle.service;


import com.example.Waffle.dto.RoomDto;
import com.example.Waffle.dto.UserRoomDto;
import com.example.Waffle.entity.GroupEntity;
import com.example.Waffle.entity.RoomEntity;
import com.example.Waffle.entity.UserEntity;
import com.example.Waffle.entity.UserRoom.UserRoomEntity;
import com.example.Waffle.exception.ErrorCode;
import com.example.Waffle.exception.UserException;
import com.example.Waffle.repository.GroupRepository;
import com.example.Waffle.repository.RoomRepository;
import com.example.Waffle.repository.UserRepository;
import com.example.Waffle.repository.UserRoomRepository;
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
    private final JwtTokenProvider jwtTokenProvider;
    private final PlanService planService;

    @Transactional
    public Long createRoom(RoomDto roomDto, String accessToken, int groupId){
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

    @Transactional
    public List<RoomEntity> getRooms(GroupEntity groupEntity){
        return roomRepository.findAllByGroupId(groupEntity);
    }

    public String roomList(String accessToken, int groupId){

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
            List<RoomEntity> roomEntities = getRooms(groupEntity);

            JSONArray roomArr = new JSONArray();

            //룸 목록 JSON 객체에 저장
            for(RoomEntity roomEntity : roomEntities){
                JSONObject room = new JSONObject();

                room.put("room_name", roomEntity.getName());
                room.put("room_id", roomEntity.getId());
                room.put("type", roomEntity.getType());

                //UserRoom에서 사용자가 해당 룸에 초대된 사람인지 조회
                Optional<UserRoomEntity> userRoomEntity = userRoomRepository.findByUserIdAndRoomId(userEntity.getId(), roomEntity.getId());
                if(userRoomEntity.isPresent()){ //초대되었으면
                    room.put("manager", userRoomEntity.get().getManager());
                    room.put("include", 1);
                }
                else{ //초대 되지 않았으면
                    room.put("manager", 0);
                    room.put("include", 0);
                }
                roomArr.put(room);
            }
            roomList.put("room", roomArr);

        }catch(Exception e){
            throw new UserException(ErrorCode.CANT_FINDROOM);
        }

        return roomList.toString();
    }

    @Transactional
    public void inviteUser(int roomId, String email){

        //email로 user 정보 찾기
        UserEntity userEntity = userRepository.findByemail(email).orElseThrow(
                () -> new UserException(ErrorCode.NO_USER)
        );

        //roomId로 room 정보 찾기
        RoomEntity roomEntity = roomRepository.findById(roomId).orElseThrow(
                () -> new UserException(ErrorCode.NO_ROOM)
        );


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
    public void deleteRoom(Long roomId){

        try {
            //roomId로 room 정보 찾기
            RoomEntity roomEntity = roomRepository.findById(roomId).orElseThrow(
                    () -> new UserException(ErrorCode.NO_ROOM)
            );

            //room에 속한 plan 모두 삭제
            planService.allDeletePlan("room", roomId);

            userRoomRepository.deleteByRoomId(roomEntity.getId());

            roomRepository.deleteById(roomEntity.getId());

        }catch(Exception e){
            throw new UserException(ErrorCode.INTER_SERVER_ERROR);
        }

    }
}
