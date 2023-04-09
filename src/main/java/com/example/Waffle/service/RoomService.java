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
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final RoomRepository roomRepository;
    private final UserRoomRepository userRoomRepository;

    @Transactional
    public void createRoom(RoomDto roomDto, String email, int groupId){

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

    }

    @Transactional
    public List<RoomEntity> getRooms(int groupId){
        return roomRepository.findAllByGroupId(groupId);
    }

    public void roomList(String email, int groupId){

        //email로 user 정보 찾기
        UserEntity userEntity = userRepository.findByemail(email).orElseThrow(
                () -> new UserException(ErrorCode.NO_USER)
        );

        JSONObject roomList = new JSONObject();
        try{
            JSONArray roomArr = new JSONArray();
//            for(){
//                JSONObject room = new JSONObject();
//                room.put("room_name", );
//                room.put("room_id", );
//                room.put("manager", );
//                room.put("type", );
//                room.put("include", );
//                roomArr.put(room);
//            }
            roomList.put("room", roomArr);

        }catch(Exception e){

        }


    }
}
